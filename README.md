# MonikaBot
A Monika Discord bot which posts quotes and images.

MonikaBot is a Java Maven project, built with [Discord4J Version 2.10.1](https://mvnrepository.com/artifact/com.discord4j/Discord4J/2.10.1)

# Commands
**/monika**  *Posts a random quote*  
**/monika #**  *Posts the quote specified*  
**/index**  *Updates the image list and posts the totals*  
**/sfwMonika**  *Posts a random sfw Monika pic*  
**/nsfwMonika**  *Posts a random nsfw Monika pic*  
**/enableNSFW**  *Enables /nsfwMonika in the current channel (Disabled by default)*  
**/disableNSFW**  *Disables /nsfwMonika in the current channel (Disabled by default)*

# Installation
* Go to the [Discord Developers Page](https://discordapp.com/developers/applications/) and create an application. There are guides that can walk you through setting up a bot.
* Download either the source code or the version that's prebuilt. If you download the source, you can edit the bot's token in MainRunner.java to avoid the extra hassle of passing your Token as an argument every time you want to start it.
* Fill up the image folders with images of Monika. I used [Grabber](https://github.com/Bionus/imgbrd-grabber) to get all my images. You can use whatever method you want though.
* In command line, navigate to the folder where the image folders and the compiled .jar file are, then run the jar file. Be sure to pass your bot's authorization token as an argument if you haven't gone into the source code and modified it yourself.

**Example: **
C:\Users\apaz>cd Desktop

C:\Users\apaz\Desktop>cd MonikaBot Prebuilt

C:\Users\apaz\Desktop\MonikaBot Prebuilt>java -jar MonikaBot.jar NTU1OTYyNjI1OTY0Mzc2MDY0.D2y6lQ.dUX0f7QZhSvAhclv1qubmqfdlu8
