# milkyway

### Overview
App developed using MVP architecture and elements of Clean Architecture (package level separation of Presentation (`ui`), Domain (`domain`) and Data (`data`) layers). 
<br>3rd party libaries used: RxJava, Retrofit, Dagger, Gson, Picasso, Espresso, Mockito.
<br>UI and Unit tests included.


Simplifications: 
<br>For simplicity Activities are used. Data is simply passed from main activity to detail activity as no aditional data is needed in detail activity. (In a more complex scenario a different mechanism would have been used).
For the same reason, detail activity has no Presenter as no user input is taking place or data requested.
