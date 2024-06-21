onload()

function onload(){
    var themeBtn = document.getElementById("theme_change_btn");
    themeBtn.addEventListener("click", changeTheme );
    var theme = getTheme()=="dark" ? "light" : "dark" ;
    setTheme(theme)
    changeTheme()
}

function changeTheme() {
   let currTheme= getTheme();
    let newTheme = currTheme=="dark" ? "light" : "dark" ;
    document.querySelector("html").classList.remove(currTheme);
    document.querySelector("html").classList.add(newTheme);
    setTheme(newTheme);
    document.querySelector("#theme_change_btn span").innerHTML=currTheme ;
}

function setTheme(mode){
    localStorage.setItem("theme", mode)
}

function getTheme(){
    let theme = localStorage.getItem("theme")
    return theme ? theme : "dark" ;
}
