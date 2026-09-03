document.addEventListener('DOMContentLoaded', function () {
  document.querySelectorAll('.nav-toggle').forEach(function (btn) {
    btn.addEventListener('click', function () {
      var nav = btn.closest('.site-nav');
      if (!nav) return;
      var isOpen = nav.classList.toggle('open');
      btn.setAttribute('aria-expanded', isOpen ? 'true' : 'false');
    });
  });

  // Close the menu automatically once a link is tapped
  document.querySelectorAll('.site-nav .nav-links a').forEach(function (link) {
    link.addEventListener('click', function () {
      var nav = link.closest('.site-nav');
      if (nav) nav.classList.remove('open');
    });
  });
});
