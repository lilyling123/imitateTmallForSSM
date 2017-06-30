package com.tmall.controller;

import com.tmall.packPojo.CategoryPack;
import com.tmall.packPojo.ProductImagePack;
import com.tmall.packPojo.ProductPack;
import com.tmall.packPojo.PropertyValuePack;
import com.tmall.pojo.*;
import com.tmall.service.*;
import com.tmall.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/25.
 */
@Controller
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ProductImageService productImageService;
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/admin_product_add")
    public String addProduct(Product p) {

        productService.addProduct(p);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_delete")
    public String deleteProduct(Integer id) {
        Product p = productService.selectProductById(id);
        productService.deleteProductById(id);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_edit")
    public String editProduct(Integer id, Model model) {
        ProductPack p = productService.selectProductById(id);
        CategoryPack c = categoryService.selectCategoryById(p.getCid());

        p.setCategory(c);

        model.addAttribute("p", p);
        return "/admin/editProduct";
    }

    @RequestMapping("/admin_product_update")
    public String updateProduct(Product p) {
        productService.updateProduct(p);
        return "redirect:/admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_editPropertyValue")
    public String editPropertyValue(Integer id, Model model) {
        ProductPack p = productService.selectProductById(id);
        CategoryPack c = categoryService.selectCategoryById(p.getCid());
        p.setCategory(c);
        List<PropertyValuePack> lists = propertyValueService.findPropertyValueList(p.getId());
        //查找到所有cid对应的property
        List<Property> pts = propertyService.findPropertyListByCid(p.getCid());
        //插入到数据库中
        for (Property pt : pts) {
            PropertyValuePack pack = propertyValueService.selectPropertyValuePackByIdAndPid(p.getId(), pt.getId());
            if (null == pack) {
                pack = new PropertyValuePack();
                pack.setProduct(p);
                pack.setProperty(pt);

                propertyValueService.addPropertyValue(pack);
            }
        }

        model.addAttribute("p", p);
        model.addAttribute("pvs", lists);

        return "admin/editProductValue";
    }

    @RequestMapping("/admin_product_updatePropertyValue")
    @ResponseBody
    public String updatePropertyValue(String value, Integer pvid) {
        PropertyValue pv = propertyValueService.selectPropertyValueById(pvid);
        pv.setValue(value);
        propertyValueService.updatePropertyValue(pv);
        return "success";
    }

    @RequestMapping("/admin_productImage_list")
    public String adminProductImageList(Integer pid, Model model) {
        //查询属性
        ProductPack p = productService.selectProductById(pid);
        List<ProductImagePack> pisSingle = productImageService.findProductImageByPid(pid, ProductImageService.type_single, 0, Short.MAX_VALUE);
        List<ProductImagePack> pisDetail = productImageService.findProductImageByPid(pid, ProductImageService.type_detail, 0, Short.MAX_VALUE);

        //设置属性
        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }

    @RequestMapping("/admin_productImage_add")
    public String addProductImage(ProductImage productimage, MultipartFile filepath) {
        ProductPack p = productService.selectProductById(productimage.getPid());
        //上传图片到fastdfs服务器
        String url = uploadFile(filepath);
        if (url != "") {
            productimage.setImgUrl(url);
        }
        productImageService.addProductImage(productimage);
        return "redirect:/admin_productImage_list?pid=" + p.getId();
    }

    @RequestMapping("/admin_productImage_delete")
    public String deleteProductImage(Integer id) {
        ProductImagePack pack = productImageService.selectProductImagePackById(id);

        productImageService.deleteProductImage(id);

        return "redirect:/admin_productImage_list?pid=" + pack.getPid();
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
