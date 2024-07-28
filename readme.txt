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
 
 You can manage login and logout page/url using httpSecuty in securityFilterChain
 
 
 
 ## google OAuth2 
 
 Add oauth2 dependancy 
 create project on google cloud conslole
 	OAuth consent screen
 	create Credentials (OAuth2 client id)
 	get secret key and and client id
 	
 add oauth2 configureation in aplication.properties
 	spring.security.oauth2.client.registration.google.client-name=google
	spring.security.oauth2.client.registration.google.client-id=
	spring.security.oauth2.client.registration.google.client-secret=
	spring.security.oauth2.client.registration.google.scope=email,profile
	
Add oauth2 configureation in securityconfig file
	httpSecurity.oauth2Login(oauth ->{
        	oauth.loginPage("/login");
        	oauth.successHandler(oauthHandler);
        });
        
Also create successhandler class
 
whene you are using httpSecurity.oauth2Login(Customizer.withDefaults()))
where you can get link of google login.
You can use this login link on your login.html
 
On successhandler class, you are geeting data of user,you can save this in DB.
 
        
