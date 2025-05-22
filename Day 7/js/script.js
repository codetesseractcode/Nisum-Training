// Navigation functionality for the SPA

document.addEventListener('DOMContentLoaded', function() {
    // Get all navigation links
    const navLinks = document.querySelectorAll('nav a');
    
    // Add click event listeners to each navigation link
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Remove active class from all links and pages
            navLinks.forEach(navLink => navLink.classList.remove('active'));
            document.querySelectorAll('.page').forEach(page => page.classList.remove('active'));
            
            // Add active class to clicked link
            this.classList.add('active');
            
            // Get the target page id from the href attribute
            const targetId = this.getAttribute('href');
            
            // Add active class to the target page
            document.querySelector(targetId).classList.add('active');
        });
    });
});
