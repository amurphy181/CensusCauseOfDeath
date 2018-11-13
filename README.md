## Stream-Based Sorting Program
<hr>

### Purpose
I put this program together in order to get some practice with Java streams. At the start, I was passingly familiar with this technology; now, I have a stronger understanding of streams and can more ably use them.

### Using the Program
There are two sections to this program: the first is the "PresidentStream" class, which I put together first in order to get the basics of streams; the second is the more interesting program: for this portion, I pulled a cause-of-death .csv file from the US Census Bureau. Rather macabre, sure, but a large and well-structured dataset.

The program starts with a prompt for the user to input values for a US state, a cause of death, and a year. These values work together to concatenate a file name (in .txt format) and are then passed along to the "censusFilter" method. In this method, the census Stream object pulls data in line-by-line from the .csv file, splits it on the commas, then filters based on the state, COD, and year data passed from the "queryInput" method. The data is then sorted using a custom comparator, which first sorts by state and then by year within the given state. Finally, the stream finishes with a forEach method that prints the data to the file location created from the search parameters.

Once completed, the "resource" file is populated with the pertinent data and is ready for viewing in your favorite text editor.

### Techniques and Technologies

### Problems Overcome

#### Current version 1.0
