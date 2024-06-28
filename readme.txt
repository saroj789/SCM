tailwindcss
    npm init -Y
    add  "type": "module", in package.json
    npm install -D tailwindcss
    npx tailwindcss init
    add content: ["./src/main/resources/**/*.{html,js}"] in tailwind.config.js file
    craete file at src\main\resources\static\css\input.css and write @tailwind base; @tailwind components; @tailwind utilities;
    
    Run the CLI tool to scan your template files for classes and build your CSS.
        npx tailwindcss -i src\main\resources\static\css\input.css -o src\main\resources\static\css\output.css --watch

     <link rel="stylesheet" data-th-href="@{'css/output.css'}"/> in your html

add Failwind CSS component library


## form spring security
add dependancy
create securityconfig(@Configuration) classes
create authenticationProvider method bean.
use DaoAuthenticationProvider object and setPasswordEncoder and setUserDetailsService,
To setPasswordEncoder create passwordEncoder method bean which returns BCryptPasswordEncoder object.
to setUserDetailsService, User implements UserDetails and override methods.
then create customUserdetaiService (@Service) which  implements implements UserDetailsService and override loadUserByUsername method (@bean)
this method will return UserDetails object.

autowired customUserdetaiService in securityconfig and pass to setUserDetailsService in daoAuthenticationProvider method
 BUT we are not able to access pages without login.


 So for access some pages without login we have to create securityFilterChain bean.
 In securityFilterChain bean you can restrict, permit and config login logout page.
        
