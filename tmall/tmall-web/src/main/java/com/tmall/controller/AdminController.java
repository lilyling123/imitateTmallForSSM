package com.tmall.controller;

import com.tmall.packPojo.ProductPack;
import com.tmall.packPojo.PropertyPack;
import com.tmall.pojo.Category;
import com.tmall.pojo.User;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyService;
import com.tmall.service.UserService;
import com.tmall.utils.FastDFSClient;
import com.tmall.utils.JsonUtils;
import com.tmall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/admin/index.html")
    public String adminIndex() {

        return "admin/index";
    }

    @RequestMapping("/admin_user_list")
    public String findAllUsers(@RequestParam(value = "page.start", defaultValue = "0") Integer start,
                               @RequestParam(defaultValue = "5") Integer count,
                               Model model) {
        //获取属性
        Page page = new Page(start, count);
        Integer total = userService.findUserTotalNumber();
        page.setTotal(total);
        int startPage = start / count + 1;
        List<User> list = userService.findUserList(startPage, count);
        //设置属性
        model.addAttribute("page", page);
        model.addAttribute("us", list);
        return "admin/listUser";
    }

    @RequestMapping("/admin_category_list")
    public String categoryList(@RequestParam(value = "page.start", defaultValue = "0") Integer start,
                               @RequestParam(defaultValue = "5") Integer count,
                               Model model) {
        //设置分页
        Page page = new Page(start, count);
        int startPage = start / count + 1;
        //查询总页数
        page.setTotal(categoryService.findCategoryTotalNumber());
        //查询分页列表
        List<Category> thecs = categoryService.findCategoryList(startPage, count);
        //设置属性到model中
        model.addAttribute("thecs", thecs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("/admin_property_list")
    public String adminPropertyList(@RequestParam(value = "page.start", defaultValue = "0") Integer start,
                                    @RequestParam(defaultValue = "5") Integer count,
                                    @RequestParam(required = false) Integer cid,
                                    Model model, HttpServletRequest request) {
        //获取cid
        if (cid == null)
            cid = (Integer) request.getSession().getAttribute("cid");
        request.getSession().setAttribute("cid", cid);
        Page page = new Page(start, count);
        //查询需要的对象
        Category c = categoryService.selectCategoryById(cid);
        int startPage = start / count + 1;
        List<PropertyPack> propertyPacks = propertyService.findPropertyList(cid, startPage, count);
        Integer total = propertyService.findPropertyTotalNumber(cid);
        page.setTotal(total);

        //设置属性
        model.addAttribute("page", page);
        model.addAttribute("ps", propertyPacks);
        model.addAttribute("c", c);

        return "admin/listProperty";
    }

    @RequestMapping("/admin_product_list")
    public String adminProductList(@RequestParam(value = "page.start", defaultValue = "0") Integer start,
                                   @RequestParam(defaultValue = "4") Integer count,
                                   @RequestParam(required = false) Integer cid,
                                   Model model, HttpServletRequest request) {
        //获取属性
        if (cid == null)
            cid = (Integer) request.getSession().getAttribute("cid");

        //用session是不安全的，后面可以改成redis
        request.getSession().setAttribute("cid", cid);

        Page page = new Page(start, count);
        int startPage = start / count + 1;
        //执行Service
        List<ProductPack> list = productService.findProductListByCid(cid, startPage, count);
        Integer total = productService.findProductListTotalNumber(cid);
        page.setTotal(total);
        Category c = categoryService.selectCategoryById(cid);
        //设置属性
        model.addAttribute("ps", list);
        model.addAttribute("page", page);
        model.addAttribute("c", c);

        return "admin/listProduct";
    }

    @RequestMapping("/admin_category_edit")
    public String adminCategoryEdit(Integer id, Model model) {

        Category category = categoryService.selectCategoryById(id);
        model.addAttribute("c", category);
        return "admin/editCategory";
    }

    @RequestMapping("/admin_category_delete")
    public String adminCategoryDelete(Integer id) {

        categoryService.deleteCategoryById(id);
        return "redirect:/admin_category_list";
    }

    @RequestMapping(value = "/admin_category_add", method = RequestMethod.POST)
    public String adminCatrgoryAdd(Category category, MultipartFile uploadFile) {
        //将图片上传到fastDFS图片服务器上
        String json = uploadFile(uploadFile);
        Map map = JsonUtils.jsonToPojo(json, Map.class);
        String url = "";
        if (map.get("error").equals(0)) {
            url = (String) map.get("url");
        }
        category.setImgUrl(url);
        //添加到数据库中
        categoryService.addCategory(category);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_update")
    public String adminCategoryUpdate(Category category, MultipartFile uploadFile) {

        //将图片上传到fastDFS图片服务器上
        String url = uploadFile(uploadFile);

        if (url != "") {
            category.setImgUrl(url);
        }
        //更新数据库
        categoryService.updateCategory(category);

        return "redirect:/admin_category_list";
    }


    private String uploadFile(MultipartFile uploadFile) {
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //得到一个地址和文件名
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //补充为完整的url
            url = IMAGE_SERVER_URL + url;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
