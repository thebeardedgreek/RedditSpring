package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;


@Controller
public class HomeController {

    @Autowired
    RedditRepository redditRepository;

    @RequestMapping("/")
    public String listReddits(Model model){
        model.addAttribute("reddits", redditRepository.findAllByOrderByDateDesc());
        return "redditlist";
    }

    @GetMapping("/add")
    public String loadFormPage(Model model){
        model.addAttribute("reddit", new Reddit());
        return "redditform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Reddit reddit, BindingResult result){
        if(result.hasErrors()){
            return "redditform";
        }
        reddit.setDate(LocalDateTime.now());
        redditRepository.save(reddit);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String displayReddit(@PathVariable("id") long id, Model model){
        model.addAttribute("reddit", redditRepository.findById(id).get());
        return "displayreddit";
    }

    @RequestMapping("/update/{id}")
    public String updateReddit(@PathVariable("id") long id, Model model){
        model.addAttribute("reddit", redditRepository.findById(id).get());
        return "redditform";
    }

    @RequestMapping("/delete/{id}")
    public String delReddit(@PathVariable("id") long id){
        redditRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search",searchString);
        model.addAttribute("reddits", redditRepository.findAllByTitleContainingIgnoreCase(searchString));
        return "redditlist";
    }

}