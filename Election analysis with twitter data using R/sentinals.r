
##The Sentials

library(Rstem)
library(sentiment)
library(qdap)
library(plyr)
library(ggplot2)
library(wordcloud)
library(RColorBrewer)

# remove retweet entities
sentinal = gsub("(RT|via)((?:\\b\\W*@\\w+)+)", "", sentinal)
# remove at people
sentinal = gsub("@\\w+", "", sentinal)
# remove punctuation
sentinal = gsub("[[:punct:]]", "", sentinal)
# remove numbers
sentinal = gsub("[[:digit:]]", "", sentinal)
# remove html links
sentinal = gsub("http\\w+", "", sentinal)
# remove unnecessary spaces
sentinal = gsub("[ \t]{2,}", "", sentinal)
sentinal = gsub("^\\s+|\\s+$", "", sentinal)


# classify emotion
class_emo = classify_emotion(sentinal, algorithm="bayes", prior=1.0)
# get emotion best fit
emotion = class_emo[,7]
# substitute NA's by "unknown"
emotion[is.na(emotion)] = "unknown"

# classify polarity
class_pol = classify_polarity(sentinal, algorithm="bayes")
# get polarity best fit
polarity = class_pol[,4]

# data frame with results
sent_df = data.frame(text=sentinal, emotion=emotion,
                     polarity=polarity, stringsAsFactors=FALSE)

# sort data frame
sent_df = within(sent_df,
                 emotion <- factor(emotion, levels=names(sort(table(emotion), decreasing=TRUE))))


# plot distribution of emotions
png("Sentinals.png", width=1280,height=800)
ggplot(sent_df, aes(x=emotion)) +  geom_bar(aes(y=..count.., fill=emotion)) +
  scale_fill_brewer(palette="Dark2") +
  ggtitle("Sentiment Analysis of Tweets:\n(classification by Emotion)") +
  theme(legend.position="right") + ylab("Number of Tweets") + xlab("Emotion Categories")
dev.off()

# plot distribution of polarity
png("Polars.png", width=1280,height=800)

ggplot(sent_df, aes(x=polarity)) +
  geom_bar(aes(y=..count.., fill=polarity)) +
  scale_fill_brewer(palette="RdGy") +
     ggtitle("Sentiment Analysis of Tweets:\n(classification by Polarity)") +
         theme(legend.position="right") + ylab("Number of Tweets") + xlab("Polarity Categories")
       
