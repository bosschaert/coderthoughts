function onMyButtonClick() {
    var y = document.getElementById('forFun');
    y.innerHTML =
"<div class='accordion' id='accordion2'>" +
"  <div class='accordion-group'>" +
"    <div class='accordion-heading'>" +
"      <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordion2' href='#collapseOne'>" +
"        Collapsible Group Item #1" +
"      </a>" +
"    </div>" +
"    <div id='collapseOne' class='accordion-body collapse in'>" +
"      <div class='accordion-inner'>" +
"        Anim pariatur cliche..." +
"      </div>" +
"    </div>" +
"  </div>" +
"  <div class='accordion-group'>" +
"    <div class='accordion-heading'>" +
"      <a class='accordion-toggle' data-toggle='collapse' data-parent='#accordion2' href='#collapseTwo'>" +
"        Collapsible Group Item #2" +
"      </a>" +
"    </div>" +
"    <div id='collapseTwo' class='accordion-body collapse'>" +
"      <div class='accordion-inner'>" +
"        Anim pariatur cliche..." +
"      </div>" +
"    </div>" +
"  </div>" +
"</div>";
}