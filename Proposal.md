## Project IDEA Template

### Project Title:

# E-RECEIPT

### Project Description:

E-RECEIPT would take every receipt in every store you purchase something into the app, for an easy storage, sorting and extra features as budget per month based on expenses.

### Use Cases (at least 3):
Short descriptions of how someone will use your application.

1. A user will sync his phone with the store machine after a purchase to store the new receipt. ( We will probably run the app with an array of pre-made receipts to run test or create a JSONurl similar to assignment and store data there )

2. A user can check purchases in the store xxx through searching date, name of store and/or amount.

3. A user can check the items purchased and the price associated with each product.

4. A user can organize a budget based on his past month/year purchases (aimed at students to create a budget).

### Dependencies

1. Currently the receipts will be keep store at the phone or in a json format url but depending on time we might store it in an array on the phone 

### Potential Contributions of Members

Undecided probably use of Github account names.

@andrewgodbout is planning on helping us setting up an url to load the app with receipts.

@AleixMolla and @davadams will do the Activity layouts ( safeInstance, landscape and reg mode, etc ), handling of data ( JSONObject from URL and storage )and graphic interpretation of the receipt data for budgets.


### Potential hurdles

Time will definitely be an issue if we are focused too much on the UI elements. Having each person commit by a deadline might become challenging if the problem requires external help. 

Creating the JSON url might be challenging because we have a very little understanding on to create a url with multiple data stored in JSON format.

Doing the extra UI elements for budget can be tricky.


### Target Audience / Existing Similar Apps

The target audience are students, because they can plan a budget since they can't have a fixed source of income. But also any person with an economic life ( works, care for budget, fixed or unfixed salary, debit or credit cards ) can find this app very useful.

### Testing

UI Test - rotation or block rotation, erroneous display of info, back buttons, graph viewing, etc.

Information Test - Display of correct messages to the user in case of user mistakes or app errors.

Bounds Checking Tests - Ensure loaded data is saved through the activities and after closing it.

### Project Timeline:

The first key is to get the Interfaces were we get new receipts and traverse through the data loaded to see details of each receipt.

Milestone 1: Interface to load new receipt from Store ( url simulation in our project ). Oct 22

Milestone 2: Sorting Interface to see all the loaded receipts and Fragment layout to display the chosen receipt data singularly. Oct 29

Milestone 3: Keep previous data loaded at the app and functionality to be able to delete old or meaningless receipts. Nov 5

Milestone 4: New Budget activity to show statistics on week/monthly expenses. Nov 12

Milestone 5: Add functionalities to the budget activity ( button to change graph info, etc ). Nov 19

Milestone 6: In-class presentation. Nov 28 or 30.

Milestone 7: Complete project report and Application. Dec 1.