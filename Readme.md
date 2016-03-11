# Readme.txt

*just a heads up, this is a work in progress. Just granting access now that there's something to see*

This document details some preparations made before and during development of the test project

## Current Outlook App
### basic notes
 - agenda dates go from jan 17 to feb 19 (33 days) (not consistent)
 - the monthly view always has 7 columns for days of the week, and 6 columns for weeks (plus one for day labels). This is true even when the selected month does not fill 6 weeks worth of space
 - (Feb 2015 only takes up 4 weeks)
 - in monthly view, clicking on a day not in "current" month does nothing
 - monthly view goes out at least 100 years, goes back 100 as well, effectively "infinite"
 - On monthly calendar shown, immediately makes all space, then calendar slides in.
 - On month cell, a blue dot is shown for days with events. 

### Event List
    sticky headers
    if no events for date, "No event" entry appears

### Issues With Current App
 - sometimes the monthly day indicator jumps back a day when clicked
 - returning to open monthly view from locked screen (activity recreation?) can result in monthly animating to current date.
 - the event indicator dots on the month view don't recycle properly. if you drag across multiple months without ever letting the calendar view settle, you'll see data from months past. 
 - on march, select the 31st. go to april, moves to 30th. Go back, still on 30th. 


### todos
There are some //todo's in the code, which represent things that I would want to get to in a production app, but feel outside of the current scope

## Architecture
### On using the Android Calendar object
There are a number of options for Dates and Times on android. 
Date is built into java, but most of the useful stuff is deprecated
JodaTime's DateTime is heavy and third party
Jake Wharton's JSR-310 backport looks promising, but it's third party, and I haven't worked with it enough to know whether it can handle this use case
Calendar can be cumbersome to use, but it's first-party, and I've used it in similar situations to good effect. 

### For Calendar "pager", recyclerview or viewpager
calendar view, recyclerview or viewpager?
both are options
Went with Recyclerview because it's intended to be much more flexible than ViewPager. Modifying it for this use case feels much more like working with it than hacking around it. 

And, now that I'm done, I get the beginnings of some nice libraries. (I've wanted better smooth-scroll for some time, and recyclerview snapping is a common request around the office)

### On the Calendar model
Create a tree, where each level represents a unit of time
All-Time->Years->Months->Days->Events (by start time)
to render, flatten the tree to a list of days

To make infinite: 

Keep a small subset of years in the recyclerview at a time. As you approach the limits of that, add a year to the side you're approaching, remove a year from the other side. 

Recyclerview should automatically keep its position

any year will have models for all of its months, and any month will have models for all of its days. this results in 3 years * 365 days worth of models, which isn't crazy.

If it became a burden, you could probably make most of these collections sparse, eliminating days/months/years without any data inside them. 

This model lends itself well to an Observable model, where data is calculated or pulled from the network as needed. If you're about to load a month you don't have, you request it, and the UI will wait. 

### On Creating a lot of objects (which this demo does)
I very much agree with the idea that you shouldn't optimize until you can prove, though benchmarks, that you need to. 

The demo creates a fairly substantial backing model to keep track of all of its events. It is likely very possible to optimize this, but I think it would be at the expense of some amount of clarity. 

### Calendar PageView
originally went with a recyclerview based implementation, but that ended up being slow since it required calling notifydatasetchanged whenever the date was changed for the calendar. 

Switching to a more directly-accessible gridlayout improved performance significantly, as well as simplified the code.
