//local image
var img_holder = "file:///android_asset/img_holder.svg";
var img_loading = "file:///android_asset/img_loading.svg";
var img_error = "file:///android_asset/img_error.svg";
//store real img urls
var imageSrcs = [];
//store all img tags
var images = [];
//start
start();

function handleImgTags() {
    //get all img tags in news content
    images = document.getElementsByTagName("img");
    imageSrcs = [];
    for (var i = 0; i < images.length; i++) {
        var img = images[i];
        imageSrcs[i] = img.src;
        img.setAttribute("src-r", img.src);
        img.setAttribute("idx", i);
        img.removeAttribute("width");
        img.removeAttribute("height");
        img.removeAttribute("style");
        //if tag img wrapped with tag a, remove tag a
        var a = img.parentNode;
        if (a != null && a.tagName == "A") {
            var p = a.parentNode;
            p.replaceChild(img, a);
        }
        if (isAutoLoadImage()) {
            showImage(img);
        } else {
            img.setAttribute("src", img_holder);
            img.onclick = function () {
                showImage(this);
            }
        }
    }
}

function showImage(img) {
    var tmp = new Image();
    img.setAttribute("src", img_loading);
    tmp.src = img.getAttribute("src-r");
    // image load success
    tmp.onload = function () {
        img.setAttribute("src", img.getAttribute("src-r"));
        img.onclick = function () {
            // when success, click again to view image in Activity
            openImage(this);
        };
    }
    // image load fail
    tmp.onerror = function () {
        img.setAttribute("src", img_error);
        img.onclick = function () {
            // when fail, click to load again
            showImage(this);
        };
    };
}

function start() {
    //get all img tags
    handleImgTags();
    //modify background to fit app theme
    if(isNightMode()){
    var body=document.getElementById("main");
    body.style.backgroundColor="#313131";
    body.style.color="#c7c7c7";
    }
}

function openImage(obj) {
    Android.openImageInActivity(obj.getAttribute("idx"), imageSrcs);
    return false;
}

function isNightMode() {
    return Android.isNightMode();
}

function isAutoLoadImage() {
    return Android.isAutoLoadImage();
}