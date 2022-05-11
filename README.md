# Further Programming Assignment 2 

**Author: Rohan Poorun s3843078**

# Description:

This project consists of a table booking and allocation system for the theoretical "ARUB" company. It uses Java as the primary language with JavaFX as the front end implementation with an integrated mySQL database.

# Running the program:

The program is run from the Main class. This can be found in src/main/Main.java. This is the file that needs to be executed for the program to run and compile. The way it has been designed to run currently is in IntelliJ as it has good JavaFX support.

# Design Pattern:

This program implements an MVC (Model View(in this program called 'ui') and Controller) design for each .fxml file. It also implements a singleton class called "Datastore" which stores a single instance of multiple key values that need to be accessed. For example: it stores a users' information such as username because at any given point only one user is logged in, thus singleton is used.

# Feature Summary:
All features listed on specification sheet have been achieved apart from the bonus mark of not allowing an employee to sit next to the same employee.

- Login Feature: Two users, Employee and Admin
- Register Feature: Can register a new user as an Employee or Admin
- Change Password Feature (secretQuestion): If a user forgets their password

**Employee Specific Features:**

- Make a booking: User cannot sit on same seat and also has functionality of COVID-19 restrictions. Uses a UI which shows a visual depiction of the tables.
- View/Cancel current booking: User can view their current booking and cancel if there's still 48 hours or more left till booking
- Check in: User can check in to their booking on the relevant day
- Change account details: User can alter all non system specific data, eg. Their username but not their employeeID
- Change password: If employee knows their current password but wishes to change their current one to a new one

**Admin Specific Features:**

- View Current Table Allocation: admin can view the table booking UI but cannot place bookings using their admin account
- Set social distancing restrictions: Admins are able to set restrictions: Social Distancing (Tables 1.5m minimum space between) or Complete Lockdown where all tables are locked down for that particular day. Admin can set these restrictions similar to bookings: For any day in the future.
- Admin booking management: Admins can accept and reject bookings
- Admin account management: Admin can edit user details, add and delete users
- Generate Admin reports: Admin can generate reports of Bookings and Employee database in .csv format at that particular time and date.

# Design Changes:
There were not too many design changes, however they include:

- When a table has a pending booking it appears blue instead of green, so a user knows and can decide whether they wish to select a different table.
- The database connection class now implements a loop. Essentially, if there is no existing Database connection, it will create a new one, otherwise it will close the current open one then create a new one.
- I also implemented a singleton class to easily transport data across classes where there only needs to be a single instance of it. I chose to implement this over DTOs and DAOs as I believed it was far more simple and reduced logic complexity and code considerably.

# Lessons Learnt:
This was the first time I had used a UI in a coding project, and thus I gained valuable knowledge in doing so. Whilst the MVC pattern was theoretically easy to understand, it took a bit of practice implementing it, however I now am very comfortable using it. There were times however where I felt conflicted as I prioritised code understandability over code minimisation. In saying that, I felt as if the biggest improvement was my implementation of code modularity. My first assignment largely consisted of monolithic code, so I made an effort to ensure this time it is modular and methods can be reused to reduce code. 

JavaFX was for the most part easy to use and learn, especially with the help of SceneBuilder. There were many tutorials online to assist with how to use different elements of JavaFX. The use of the singleton class was at first confusing and hard to implement, however after I understood it's application and use, and was finally able to implemented it properly, it proved extremely useful and invaluable.

The use of mySQL and databases was at first quite difficult as I was not too confident with SQL. However, my understanding has greatly improved, and I feel much more comfortable. Database errors were easily my most common error, however I was able to fix those by ensuring I always create and close a new connection, as well as a slight tweaking of the Database connect class and ensuring all PreparedStatements and ResultSets were closed too.

One of the things I was not able to achieve was enabling full screen mode. I found this to be extremely confusing and complicated and could not get it to work. The program is still functional in full screen however it simply contains a lot of white space with page content in the top left corner. Thus, it is recommended the program is used in its designed minimised form.

