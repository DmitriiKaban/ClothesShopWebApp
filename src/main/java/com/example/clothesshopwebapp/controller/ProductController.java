package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.*;
import com.example.clothesshopwebapp.repository.*;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ProductService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private SexRepository sexRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    public static ModelAndView getUserDetail(ModelAndView mav, AccountService accountService, ShoppingCartService shoppingCartService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals("anonymousUser")) {
            Account account = accountService.findOneByEmail(auth.getName()).get();
            mav.addObject("user_name", account.getFirstName() + ' ' + account.getLastName());
            int numItems = shoppingCartService.listCartItems(accountService.findOneByEmail(auth.getName()).get()).size();
            mav.addObject("cart_items_num", numItems);
        }
        return mav;
    }


    @GetMapping("/")
    public ModelAndView showProductsGet() {
        return listByPage(1, "price", "asc", "", "", "", "", "", "");
    }

    @GetMapping("/page/{pageNumber}")
    public ModelAndView listByPage(@PathVariable("pageNumber") int currentPage,
                                   @Param("sortField") String sortField,
                                   @Param("sortDirection") String sortDirection,
                                   @Param("brand") String brand,
                                   @Param("color") String color,
                                   @Param("type") String type,
                                   @Param("sex") String sex,
                                   @Param("size") String size,
                                   @Param("season") String season
    ) {
        ModelAndView mav = new ModelAndView("list-products");
        Page<Product> page = productService.getAll(currentPage, sortField, sortDirection, brand, color, type, sex, size, season);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Product> listOfProducts = page.getContent();
        mav.addObject("products", listOfProducts);
        mav.addObject("totalItems", totalItems);
        mav.addObject("totalPages", totalPages);
        mav.addObject("currentPage", currentPage);
        mav.addObject("sortField", sortField);
        mav.addObject("sortDirection", sortField);
        mav.addObject("brand", brand);
        mav.addObject("type", type);
        mav.addObject("color", color);
        mav.addObject("sex", sex);
        mav.addObject("season", season);
        mav.addObject("size", size);
        return getUserDetail(mav, accountService, shoppingCartService);
    }


    @GetMapping("/products/{id}")
    public ModelAndView productDetails(@PathVariable("id") Long id) {//@PathVariable Long id){
        ModelAndView mav = new ModelAndView("details-product");
        Optional<Product> product = productRepo.findById(id);
//        try {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
//                try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account account = accountService.findOneByEmail(auth.getName()).get();
            mav.addObject("user_name", account.getFirstName() + ' ' + account.getLastName());
            int numItems = shoppingCartService.listCartItems(accountService.findOneByEmail(auth.getName()).get()).size();
            mav.addObject("cart_items_num", numItems);

            if (shoppingCartService.findByProductAndAccount(product.get(), account)) {
                mav.addObject("is_in_cart", true);
            } else mav.addObject("is_in_cart", false);
//                } catch (NoSuchElementException ex) {
//                    throw new NoSuchFieldException();
//                }
        }
        mav.addObject("product", product.get());
        mav.addObject("qty", product.get().getQuantity());
        return mav;

//        } catch (TemplateInputException | NoSuchFieldException ex) {
//            System.out.println(ex.printStackTrace());
//        }
    }

    @PostMapping("/products/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String updateProduct(@PathVariable Long id, Product product, BindingResult result, Model model) {

        Optional<Product> optionalProduct = productService.getById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setBrand(product.getBrand());
            existingProduct.setColor(product.getColor());
            existingProduct.setImages(product.getImages());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setSex(product.getSex());
            existingProduct.setSeason(product.getSeason());
            existingProduct.setType(product.getType());
            existingProduct.setSize(product.getSize());

            productService.save(existingProduct);
        }

        return "redirect:/";
    }

    @GetMapping("/products/new")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String createNewProduct(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Account> optionalAccount = accountService.findOneByEmail(auth.getName());
        if (optionalAccount.isPresent()) {
            List<Type> listtype = typeRepository.findAll();
            List<Season> listseason = seasonRepository.findAll();
            List<Brand> listbrand = brandRepository.findAll();
            List<Sex> listsex = sexRepository.findAll();
            List<Size> listsize = sizeRepository.findAll();
            List<Color> listcolor = colorRepository.findAll();
            Product product = new Product();
            model.addAttribute("product", product);
            model.addAttribute("listseason", listseason);
            model.addAttribute("listbrand", listbrand);
            model.addAttribute("listsex", listsex);
            model.addAttribute("listtype", listtype);
            model.addAttribute("listsize", listsize);
            model.addAttribute("listcolor", listcolor);
            return "product_new";
        } else {
            return "404";
        }
    }

    @PostMapping("/products/new")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ModelAndView saveNewProduct(@ModelAttribute Product product, @RequestParam("img1") MultipartFile image1, @RequestParam("img2") MultipartFile image2) {

        ModelAndView mav = new ModelAndView("list-products");
        Path fileNameAndPath1 = Paths.get(uploadDirectory, image1.getOriginalFilename());
        Path fileNameAndPath2 = Paths.get(uploadDirectory, image2.getOriginalFilename());
        try {
            Files.write(fileNameAndPath1, image1.getBytes());
            Files.write(fileNameAndPath2, image2.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        productService.save(product);

        Image image = new Image();
        image.setImage_dir(image1.getOriginalFilename());
        image.setProduct(product);
        imagesRepository.save(image);
        image = new Image();
        image.setImage_dir(image2.getOriginalFilename());
        image.setProduct(product);
        imagesRepository.save(image);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            mav.addObject("user_name", auth.getName());
        }

        return mav;
    }

    @GetMapping("/products/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String getProductForEdit(@PathVariable Long id, Model model) {

        Optional<Product> optionalProduct = productService.getById(id);
        List<Type> listtype = typeRepository.findAll();
        List<Season> listseason = seasonRepository.findAll();
        List<Brand> listbrand = brandRepository.findAll();
        List<Sex> listsex = sexRepository.findAll();
        List<Size> listsize = sizeRepository.findAll();
        List<Color> listcolor = colorRepository.findAll();

        if (optionalProduct.isPresent()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                model.addAttribute("user_name", auth.getName());
            }
            Product product = optionalProduct.get();
            model.addAttribute("product", product);
            model.addAttribute("product", product);
            model.addAttribute("listseason", listseason);
            model.addAttribute("listbrand", listbrand);
            model.addAttribute("listsex", listsex);
            model.addAttribute("listtype", listtype);
            model.addAttribute("listsize", listsize);
            model.addAttribute("listcolor", listcolor);
            return "product_edit";
        } else {
            return "404";
        }
    }

    @GetMapping("/products/{id}/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            List<Image> list = imagesRepository.findByProductId(id);
            for (var item :
                    list) {
                File file = new File("C:\\Users\\dima1\\IdeaProjects\\ClothesShopWebApp\\src\\main\\resources\\static\\images\\" + item.getImage_dir());
                System.out.println(file.getAbsoluteFile());
                if (file.delete()) {
                    System.out.println(file.getName() + " is deleted!");
                } else {
                    System.out.println("Delete operation is failed.");
                }
                imagesRepository.delete(item);
            }

            productService.delete(existingProduct);
            return "redirect:/";
        } else {
            return "404";
        }

    }

//    @ExceptionHandler(value = TemplateInputException.class)
//    public ModelAndView handleException() {
//        ModelAndView mav = new ModelAndView("404");
//        mav.addObject("error_message", "Template Input Exception caused!");
//
//        return getUserDetail(mav, accountService, shoppingCartService);
//    }

}
