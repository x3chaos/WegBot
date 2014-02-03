#Wegbot

A [Twitter bot](https://twitter.com/wegbot) that tweets nonsensical things, some of which are coherent.

###Table of Contents

* What is this
* What does it do
* Why does this exist

---

##What is this

Wegbot is a little project I'd dreamt up for a while, after noticing little bots floating around Twitter like [@radio_ebooks](https://twitter.com/radio_ebooks), and, more famously, [@horse_ebooks](https://twitter.com/horse_ebooks) - though the latter isn't a fair comparison, because the algorithms used in that one differ greatly from that of Wegbot.

The bot is created entirely in Java using [Twitter4j](http://twitter4j.org/en/index.html), as well as my own data analysis code.

Sometimes it says bad words.

## What does it do

Wegbot is basically an "ebooks bot," the concept of which I won't delve into here. It performs several tasks, the most basic of which is to post tweets. The logic behind this function is:

1. Retrieve [my](https://twitter.com/x3chaos) last 100 or so tweets
2. Analyze these tweets and compile useful information from them
3. Synthesize its own randomly generated tweet based on this information

The information retrieved in step 2 consists of word order and frequency, creating a sort Markov chain that is used in step 3. More information can be found in the wiki, once I've gotten around to creating one.

## Why does this exist

Wegbot is my own personal exercise in several different aspects of programming: the code, the documentation, and the implementation. It serves no tangible purpose, obviously, except to carry out these things.

In the future, I would like to create an API for Twitter bots like this, but I can see how it would be grossly misused for things like easy-to-create spambots, and connection with that kind of dark magic is no bueno, in my opinion. We'll see if I finally decide to give in to my own curiosity and go through with it.

---

######Wegbot is my own creation, but documentation for the concept is scarce, so feel free to enjoy what I put here. I leave out the security-sensitive bits, but the useful stuff is still useful. That said, I'm not currently keeping any sort of license on this code because it's not of my own unique invention. Have at it. -- x3chaos (Shawn)