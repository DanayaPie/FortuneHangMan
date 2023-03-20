package side.project.FHM.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import side.project.FHM.service.CategoryService;

@RestController
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/category")
    public ResponseEntity<Object> getRandomWordByCategory(@PathVariable String categoryName) {
        logger.info("CategoryController.getRandomWordByCategory() invoked");


    }
}
