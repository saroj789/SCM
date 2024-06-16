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
        
