package com.nisum.controller;

import com.nisum.model.Document;
import com.nisum.model.TextEditor;
import com.nisum.dao.DocumentDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for document editing operations.
 * Following SRP by focusing only on handling HTTP requests for document operations.
 */
@Controller
public class DocumentController {

    private final TextEditor textEditor;
    private final DocumentDAO documentDAO;

    @Autowired
    public DocumentController(
            @Qualifier("completeTextEditor") TextEditor textEditor,
            DocumentDAO documentDAO) {
        this.textEditor = textEditor;
        this.documentDAO = documentDAO;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Get all documents to display on the home page
        List<Document> documents = documentDAO.findAll();
        model.addAttribute("documents", documents);
        return "home";
    }

    @GetMapping("/document/list")
    public String listDocuments(Model model) {
        // Get all documents to display on the home page
        List<Document> documents = documentDAO.findAll();
        model.addAttribute("documents", documents);
        return "home";
    }

    @GetMapping("/document/new")
    public String newDocument() {
        return "document-form";
    }

    @PostMapping("/document/create")
    public String createDocument(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model) {

        Document document = textEditor.createDocument(title, content);
        model.addAttribute("document", document);
        model.addAttribute("message", "Document created successfully!");

        return "document-view";
    }

    @GetMapping("/document/{id}")
    public String viewDocument(@PathVariable("id") Long id, Model model) {
        Document document = textEditor.openDocument(id);

        if (document != null) {
            model.addAttribute("document", document);
            return "document-view";
        } else {
            model.addAttribute("error", "Document not found");
            return "error";
        }
    }

    @GetMapping("/document/{id}/edit")
    public String editDocument(@PathVariable("id") Long id, Model model) {
        Document document = textEditor.openDocument(id);

        if (document != null) {
            model.addAttribute("document", document);
            return "document-form";
        } else {
            model.addAttribute("error", "Document not found");
            return "error";
        }
    }

    @PostMapping("/document/{id}/update")
    public String updateDocument(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            Model model) {

        Document document = textEditor.openDocument(id);

        if (document != null) {
            document.setTitle(title);
            document.setContent(content);

            document = textEditor.saveDocument(document);
            model.addAttribute("document", document);
            model.addAttribute("message", "Document updated successfully!");

            return "document-view";
        } else {
            model.addAttribute("error", "Document not found");
            return "error";
        }
    }
}


