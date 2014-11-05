Technical question answers
==========================

1) How long did you spend on the coding test? 325 mins - here is the breakdown.

**Project set-up**

* Build configuration (gradle file set-ups and fetching latest version of required dependencies) - 15 mins
* Designing web requests interface and evaluating Retrofit (not used this library previously) - 20 mins

**API modelling**

* Create skeleton implementations and used Postman (Chrome Extension) to obtain a test response from
the provided URL - 15 mins
* Restaurant search tests and correcting Retrofit errors - 60 mins

**(Side tracked)**

* Investigating whether I can use the GPS position to deduce the outcode - 30 mins
(found this - http://www.freemaptools.com/download-uk-postcode-lat-lng.htm)

**Android**

* Search view - 30 mins
* Activity and Fragment with outcode validator - 40 mins
* Fragment transitions - 25 mins
* Listing layout and render - 45 mins
* Navigation bug fixes - 45 mins
* Displaying delivery options - 30 mins

...if I had more time I would have incorporated a map-based interface that would use the user's
position to deduce their outcode. The first-time-use would then present a list of nearby restaurants
relevant to the user. Furthermore, I would have added more testing to the Android layer in readiness
for a refactor to support more Android OS versions and form factors.

2) What was the most useful feature that was added to the latest version of your chosen language?
I am looking forward to using Java 8 and the succinct
[Lambda expressions](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html).
I haven't used Lambda's in production code owing primarily to the slow uptake of Java upgrades.

3) How would you track down a performance issue in an application? I commonly encounter ````OutOfMemory````
exceptions when working with image intensive applications on Android with the preceeding 'jank' a performance issue.
Some of the investigatory work I completed (in conjunction with SoundCloud) into performance problems
with [Picasso](http://square.github.io/picasso/) are outlined in this
[SO issue](http://stackoverflow.com/questions/19687026/android-memory-management-screen-density-requested-image-sizes-and-available-h).
The work involved using MAT and JProfiler to identify which portion of the system was being memory
inefficient. I have encountered other performance related issue when performing high volume batch
processing on Java-based web-apps serving the finance industry. The fixes for these problems were (invariably) poor query performance
and overzealous loading (only to throw away the majority of the results). Correcting these sorts of
issues are best tackled as if they were enhancements and require thorough functional and technical
approvals.

4) How would you improve the JUST EAT APIs that you just used?
I do not understand how a restaurant can be identified as ````IsOpen````, ````false```` only for the
````IsOpenNowForDelivery```` and ````IsOpenNowForCollection```` flags to report ````true````!
Either the naming here is incorrect or we have a discrepancy. Furthermore, it would be good to provide
a 'navigable API' (c.f. _HAL_ specification) so that the responsiveness of the application is not
affected when searching in high-density areas such as "SE19". With this approach a paging-based
system could be employed with a lazy loading list ensuring that the user's 'loading stare' is kept
to a minimum. Finally, the ````CuisineTypes```` object could be switched over to a map representation
 - if such a move was completed we could drop the ````id```` on this object, reducing redundant information.

 5) A (brief) description of myself in JSON;

 ```
 {
   "name": "David Branton",
   "age": 29,
   "gender": "Male",
   "education": {
     "university": [
       "MEng Imperial College London"
     ],
     "college": [
       "United World College of the Atlantic"
     ]
   },
   "current_position": {
     "latitude": "51.4400242",
     "longitude": "-2.5512776"
   },
   "interests": [
     "Surfing",
     "Mountain Biking",
     "Tennis",
     "Rugby",
     "Swimming",
     "DIY"
   ],
   "likes": {
     "drinks": [
       "Doom Bar",
       "Bath Ales",
       "Hobgoblin",
       "San Pellegrino Limonata"
     ],
     "foods": [
       "Italian",
       "Indian",
       "Thai",
       "Lebanese"
     ]
   }
 }
 ```