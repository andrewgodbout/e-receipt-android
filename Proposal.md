## Project IDEA Template

### Project Title:

# E-RECEIPT

### Project Description:

E-RECEIPT would take every receipt in every store you purchase something into the app, for an easy storage, sorting and extra features as budget per month based on expenses.

A high level overview of what your app does.

_Example:_
_An app that allows people to create a portfolio of stocks that they track like apple or coca-cola and lets them graph the stock for different time periods and compare stock performance for multiple different stocks._

### Use Cases (at least 3):
Short descriptions of how someone will use your application.

1. A user will sync his phone with the store machine after a purchase to store the new receipt. ( We will probably run the app with an array of pre-made receipts to run test )

2. A user can check purchases in the store xxx or last purchases.

3. A user can show the information on receipt.

4. A user can organize budget based on his past month/year purchases.

_Example:_

_1. A user wants to check the current price of a certain stock._

_2. A user wants to be notified if the stock price is dropping lots that day_

_3. A user saves off stocks to a favorites list for which the prices are automatically loaded and displayed upon opening the app at a later time._

### Dependencies

1. Dependent on new non-existen features for stores sharing receipts.

2. Dependent on GraphView maybe for doing some graphs on budget request.

3. Currently the receipts will be keep store at the phone, maybe by security reasons will be better to have an access with the user own bank to retrieve the receipts data.

_Does your application require any support outside of what is natively onboard the phone._

_Example:_

_1. Dependent on the iex stock exchange API to get up to date stock information_

_2. Dependent on the GraphView Library or a similar graphing library we will investigate options to make cool graphs_

_3. Currently we are thinking of maintaining the stocks the customer is interested in locally on the phone but pending time we might try to save them out to a server_

### Potential Contributions of Members

Undecided probably use of Github account names.

_You are welcome to leave your actual names out of this if you'd like, your github account names are cross-listed so feel free to use those._

_Example:_

_@andrewgodbout is planning to handle the UI elements except for the graphing. He will build an interface that sits between our app and GraphView so we are not super dependent on that library and gain flexibility - this might not be evident to the users but will make our app more robust._

_at otherteam member will handle the graphing once the data is passed through the interface. Most likely this will use GraphView - and she will work with thirdteam member to save the data into a local database on the phone. She will also create some basic analytics like calculating a users losses on their stock purchases._

_at thirdteam member will figure out how to store information locally on the phone - so that we can automatically load and save stock information like the ticker symbol or previous close or even the price at which a person purchased the stock. that a person is interested in tracking. Further this member will implement the automatic queries that will run in the background and notify the user if a stock is crashing._

_The project report will be a unified effort as will the in class presentation and demonstration._

### Potential hurdles

The idea is extract from ideaswatch.com and some features to make a feasible app needs more knowledge on security of the app. Also a permits of receiving the receipt data to an app.

_Identify any hurdles you might encounter here and some potential contingency plans_

_Example:_

_Time might be a hurdle. If time is running low, then we can remove the background monitoring of stocks and team members can combine forces on the UI portion of the app._

_We do not really know how to save data on the phone nor onto a server so we do not yet really know how much work is involved with that. However we could always simulate saving the data, or just write out simple key and value pairs which should be manageable._

### Target Audience / Existing Similar Apps

The target audience are students, because they can plan a budget since they can't have a fixed source of income. But also any person with an economic life ( works, care for budget, fixed or unfixed salary, debit or credit cards ) can find this app very useful.

_Identify who might use this app and which apps already exist that are similar._

_Example:_

_Potential users - people who want to get notifications and track stock prices for apps. There are many similar apps on the app stock such as the My Stocks Portfolio app._

### Testing

Identify some ways in which your program should be tested / potential problems you might keep in mind during development / ways in the which the user may cause problems. At this point this can be a cursory set of high level tests.

Example:

UI Tests - rotation or block rotation, erroneous display of info, back buttons, graph viewing, etc.

Information Test - ...

Bounds Checking Test - ...

_UI Tests - rotation, erroneous input, back buttons, etc._

_Information Tests - Ensuring graceful fails if the API calls fail or the internet is not working, Ensuring proper messages are displayed if the user searches for non-existent stocks._

_Bounds Checking Tests - Ensuring we handle instances where the user saves off lots of data points._

### Project Timeline:



How do you envision the project progressing? Any Milestones?

Example:

The first key is to get the Interface determined that will sit between the raw stock data and the graphing library. Having that done will allow queries to the iex api and the implementation of graphing of stocks to happen concurrently.

Milestone 1: Interface between raw data and graphing library: Estimated Nov 2.

Milestone 2: Investigation and cursory ability to write out to phone's hard-drive simple information like a favorite stock: Estimated Nov 5.

We plan to work a fair bit during the reading days around Remembrance day.

Milestone 3: Basic functionality for use case 3 start to finish. User opens app, picks a stock saves it as a favorite and it automatically loads next time. Estimated Nov 15.

At this point we should step back and ensure sufficient testing is being done.

Milestone 4: Testing and debugging to ensure we avoid a build up of issues. Estimated Nov 20.

Time permitting we will pursue the background task to automatically check if stocks are crashing. Estimated November 25.

Milestone 4: Completed project report Nov 29.

Milestone 5: Completed Application Nov 29.

Milestone 6: In-class presentation. Nov 30.
