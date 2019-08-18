# MonikaBot
A Monika Discord bot which posts quotes and images.

MonikaBot is a Java Maven project, built with [Discord4J Version 2.10.1](https://mvnrepository.com/artifact/com.discord4j/Discord4J/2.10.1)


# Commands  

**/monika** *Arguments: 1 - 62*

*Posts the quote specified, or a random quote if the number is absent or that number quote could not found.*  

**/index** *Arguments: none*

*Updates the image list and posts the totals.*  

**/sfwMonika** *Arguments: none*

*Posts a random sfw Monika pic.*  


**/nsfwMonika** *Arguments: none*

*Posts a random nsfw Monika pic*  

**/enableNSFW** *Arguments: none*

*Enables **/nsfwMonika** in the current channel (Disabled by default)*  

**/disableNSFW** *Arguments: none*

*Disables **/nsfwMonika** in the current channel (Disabled by default)*


# Interserver Commands
**/listServers** *Arguments: none*

*Gets a list of servers that this bot is on.*

**/listChannels** *Arguments: ServerKeyword post|print*

*Gets a list of channels in the server specified by the keyword. This is case-sensitive, so be careful with that. Posts in discord when you type posts afterward.*

**/rip** *Arguments: ServerKeyword ChannelKeyword file|post|print*

*Collects all the links to all the attachments in the specified channel on the specified server. This is (usually) a truly massive number of images. Be careful with this command, especially when posting. Saving to a file allows you to move those images around with **/transplant.***

**/transplant** *Arguments: none, handled by **/rip** and **/rc***

*Effectively creates a GUI inside discord with which to move around the links which have been dumped into a file by **/rip.** Register channels first with **/registerchannel** or it's alias **/rc,** then use this command and react to the resulting message with an emoji to move the image into that channel, or press the green X to skip that image. Type **/reset** to stop.*

**/registerChannel OR **/rc**** *Arguments: none*

*Registers this channel an emoji to transplant with. See /transplant.*

**/reset** *Arguments: none*

*Clears the global state of the interserver commands. Call this when you're done transplanting.*


# Installation
* Go to the [Discord Developers Page](https://discordapp.com/developers/applications/) and create an application. There are guides that can walk you through setting up a bot. I like [this one](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token).
* Download either the source code or the version that's prebuilt. If you download the source, you can edit the bot's token in MainRunner.java to avoid the extra hassle of passing your Token as an argument every time you want to start it.
* Fill up the image folders with images of Monika. I used [Grabber](https://github.com/Bionus/imgbrd-grabber) to get all my images. You can use whatever method you want though.
* In command line, navigate to the folder where the image folders and the compiled .jar file are, then run the jar file. Be sure to pass your bot's authorization token as an argument if you haven't gone into the source code and modified it yourself.


**Example: **

C:\Users\apaz>cd Desktop

C:\Users\apaz\Desktop>cd MonikaBot Prebuilt

C:\Users\apaz\Desktop\MonikaBot Prebuilt>java -jar MonikaBot.jar NTU1OTYyNjI1OTY0Mzc2MDY0.D2y6lQ.dUX0f7QZhSvAhclv1qubmqfdlu8
