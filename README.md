# Popular-Movies
An Android movie mobile app which displays a grid of movie posters. Developed as part of the Udacity Nanodegree course. The app lets the user to tap on any movie poster from the list of movie posters displayed on the Home Screen in the form of a grid.

## App Features and Functional Behavior

The app has got a Movie posters grid on the Main Screen which will be shown to the user when the app is launched. The screen looks like this : 
<br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/Home%20Screen.png" width="200" height = "350">
<br/>
When the user taps on a movie poster on the Main Screen, a View Pager holding the more information such as movie details, trailers and reviews of the movie which the user tapped in the Main Screen will be displayed. The information will displayed on the screen like this : 
<br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/ViewPager.png" width="200" height = "350">
<br/>
and also there's a settings screen which will let the user to sort the movies on the Main Screen grid, according to the chosen sort order from the menu.
<br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/Settings%20Screen.png" width="200" height = "350">
<br/>

User can also share the movie details with their contacts via whatsApp or any messaging app present in their device by tapping on the Share icon present in the app's app bar.
<br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/AppBar_Share.png" width="200" height = "350">
<br/>

  Users can also mark a movie as their favorite movie by tapping on the "Mark As Favorite" button Details tab of the ViewPager. This will make app to store that particular movie's information locally in the SQLite database to make that particular movie information accessibile even then the app is running offline. Once the movie marked favorite, then the button turns red in color with "Marked Favorite" written on the button to indicate that the movie is marked favorite already.
  <br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/Fav_btn.png" width="200" height = "350">
<br/>

In case, if the user wants to remove a particular movie from his/her favorite movies list. Then the user can long press on the "Marked Favorite" button. The button will turn green in color with "Mark as Favorite" written on it, and a toast message is displayed on the screen confirming the user that the movie is removed from the favorite movie list.
  <br/>
<img src="https://github.com/pa1-teja/Popular-Movies/blob/master/app/src/main/res/drawable/remove_fav.png" width="200" height = "350">
<br/>
 
 
