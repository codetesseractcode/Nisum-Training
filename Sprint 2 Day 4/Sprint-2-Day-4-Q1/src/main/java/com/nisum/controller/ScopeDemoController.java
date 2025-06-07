package com.nisum.controller;

import com.nisum.beans.AnnotatedScopeBean;
import com.nisum.beans.ScopeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller to demonstrate different bean scopes in Spring MVC
 */
@Controller
public class ScopeDemoController {

    // Inject ApplicationContext directly
    @Autowired
    private ApplicationContext applicationContext;

    // XML configured beans
    @Autowired
    @Qualifier("singletonBean")
    private ScopeBean singletonBean;

    @Autowired
    @Qualifier("prototypeBean")
    private ScopeBean prototypeBean;

    @Autowired
    @Qualifier("requestBean")
    private ScopeBean requestBean;

    @Autowired
    @Qualifier("sessionBean")
    private ScopeBean sessionBean;

    @Autowired
    @Qualifier("globalSessionBean")
    private ScopeBean globalSessionBean;

    // Annotation configured beans
    @Autowired
    @Qualifier("singletonAnnotatedBean")
    private AnnotatedScopeBean singletonAnnotatedBean;

    @Autowired
    @Qualifier("prototypeAnnotatedBean")
    private AnnotatedScopeBean prototypeAnnotatedBean;

    @Autowired
    @Qualifier("requestAnnotatedBean")
    private AnnotatedScopeBean requestAnnotatedBean;

    @Autowired
    @Qualifier("sessionAnnotatedBean")
    private AnnotatedScopeBean sessionAnnotatedBean;

    @Autowired
    @Qualifier("globalSessionAnnotatedBean")
    private AnnotatedScopeBean globalSessionAnnotatedBean;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // Get a new instance of prototype beans to demonstrate they're different
        // from the ones autowired above
        ScopeBean newPrototypeInstance = applicationContext
                .getBean("prototypeBean", ScopeBean.class);

        AnnotatedScopeBean newAnnotatedPrototypeInstance = applicationContext
                .getBean("prototypeAnnotatedBean", AnnotatedScopeBean.class);

        // Add all beans to the model
        model.addAttribute("singletonBean", singletonBean);
        model.addAttribute("prototypeBean", prototypeBean);
        model.addAttribute("requestBean", requestBean);
        model.addAttribute("sessionBean", sessionBean);
        model.addAttribute("globalSessionBean", globalSessionBean);

        model.addAttribute("newPrototypeInstance", newPrototypeInstance);

        model.addAttribute("singletonAnnotatedBean", singletonAnnotatedBean);
        model.addAttribute("prototypeAnnotatedBean", prototypeAnnotatedBean);
        model.addAttribute("requestAnnotatedBean", requestAnnotatedBean);
        model.addAttribute("sessionAnnotatedBean", sessionAnnotatedBean);
        model.addAttribute("globalSessionAnnotatedBean", globalSessionAnnotatedBean);

        model.addAttribute("newAnnotatedPrototypeInstance", newAnnotatedPrototypeInstance);

        return "home";
    }
}
