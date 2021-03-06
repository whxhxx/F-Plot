macro_analysis <- function(x,y){
#load required packages
require(quantmod)
require(pracma)
require(lattice)
require(ggplot2)
library(zoo)
library(tseries)   

setwd("/Users/Haixin/Documents/Eclipse/PlotFinancialConditionIndex")
#x is the number of period for moving standard deviation  y is the number of period for moving average
#Read data of indicators and S&P500
#SPX<-read.csv('spx.csv')[325:548,c(1,7,8,9,10)]
#data<-read.csv('leading indicators.csv')
#SPX<-read.csv('spx.csv')[277:546,c(1,7,8,9,10)]
#data<-read.csv('monthly data.csv')

#read data



original_data <- read.csv('output.csv')
##
term_spread <- na.omit((original_data[,4] - original_data[,5])/100)
credit_spread <- na.omit((original_data[,3] - original_data[,2])/100)
m2 <- diff(original_data[,6])/original_data[-length(original_data[,2]),6]
fed_fund <- na.omit(original_data[,5])
data <- data.frame(original_data[-1,1],term_spread,credit_spread,m2,fed_fund)



## read in list of constituents, with company name in first column and
## ticker symbol in second column

## specify time period
dateStart <- "1996-12-01"               
## download data on first stock as zoo object
SPX <- get.hist.quote(instrument = '^GSPC', start = dateStart
                      , quote = "AdjClose",
                      retclass = "zoo", quiet = T,compression = "m")
SPX <- data.frame(SPX)
SPX <- SPX[-length(SPX),]
SPX_return <- diff(SPX)/SPX[-length(SPX)]
SPX <- data.frame(as.vector(original_data[-1,1]),SPX[-1],SPX_return)
colnames(SPX) <- c('Data','SPX','Return')



#function calculates deviations of each indicator 
analyze<-function(data,n,m){
  #calculate moving standard deviation
  msd<-function(data,n){
    movingstd<-rep(NA,length(data))
    for(i in n : length(data) ){
      movingstd[i]<-sd(data[(i-n+1):(i-1)])
    }
    return(movingstd)    
  }
  sigma<-msd(data,n)
  a <- max(m,n)
  #calculate moving average
  ma<- SMA(data,m)
  deviation <- (data-ma)/sigma
  deviation<-na.omit(deviation)
  return(deviation)
}

#calculate deviations of indicators using function
z<-max(x,y)
deviation_table<-matrix(rep(0,((length(data[,1])-z+1)*length(data[1,]))),(length(data[,1])-z+1),length(data[1,]))
colnames(deviation_table)<-colnames(data)  

for (i in 2:length(data[1,])){
  deviation_table[,i]<-analyze(data[,i],x,y)
}

deviation_table <- data.frame(deviation_table)
deviation_table[,1]<-as.vector(data[z:length(data[,1]),1])


#number of loops
N=50000

##weighting method
pct <- 0.06   # define the shreshold 
weight_table_1 <- matrix(,N,length(deviation_table[1,])-1)
for(i in 1:N){
  c <- runif(length(deviation_table[1,])-1)
  for (a in 1:length(c)){
    b <- sum(c)
    weight_table_1[i,a] <- c[a]/b
  }
}
indicator <- matrix(,length(deviation_table[,1]),N)

#calculating the composite indices 
bind_table <- cbind(deviation_table[,-1],SPX[z:length(SPX[,2]),3])
colnames(bind_table)[length(bind_table[1,])] <- 'SPX'
linear_regression <-lm(SPX~.,data=bind_table)
weight_deviation <- array(,c(length(deviation_table[,2]),(length(deviation_table[1,])-1),N))

#determine the direction of each indicator(+ or -) using linear regression
for (n in 1:N){
  for (m in 2:length(deviation_table[1,])){
    if(summary(linear_regression)$coefficients[m,1] < 0){
    weight_deviation[,(m-1),n] <- -deviation_table[,m]*weight_table_1[n,m-1]
    }else{
      weight_deviation[,(m-1),n] <- deviation_table[,m]*weight_table_1[n,m-1]  
    }    
  }
  for(g in 1:length(deviation_table[,2])){
  indicator[g,n] <- sum(weight_deviation[g,,n])
  }  
}
#
extreme_indicator <- matrix(,floor(pct*length(indicator[,1])),N)
for (i in 1: N ){
  extreme_indicator[,i] <- head(sort(indicator[,i]),floor(pct*length(indicator[,1])))
}

#calculate accuracy
accuracy <- rep(0,N)

#define the ranges of financial crisises 
a1 <- which(deviation_table[,1]=='Oct, 1997')
a2 <- which(deviation_table[,1]=='Oct, 1998')
b1 <- which(deviation_table[,1]=='Aug, 1999')
b2 <- which(deviation_table[,1]=='Apr, 2003')
c1 <- which(deviation_table[,1]=='Jan, 2007')
c2 <- which(deviation_table[,1]=='Jan, 2010')

#Calculate the accuracy of each weighting method
for (i in 1: N){
  x <- rep(0,floor(pct*length(indicator[,1])))
  for (v in 1 : floor(pct*length(indicator[,1]))){
    g <- which(indicator[,i]==extreme_indicator[v,i])
    if (g >= a1 & g<=a2 |g >= b1 & g<=b2 | g>=c1 & g<=c2){
      x[v] <- 1
    } else{
      x[v] <- 0
    }   
  }
  accuracy[i] <- sum(x)/length(x)
}

###plot combinations with top 10 accuracy
#for (i in 1 : 10){
 # plot(indicator[,which(accuracy==tail(sort(accuracy),10))[i]],type='l',,ylab='Deviation',xaxt='n',main=paste('Weighted Deviation,','Accuracy=',tail(sort(accuracy),10)[i],sep=' '),lwd=3)
  #t <- rep(0,floor(pct*length(indicator[,1])))
  #for (r in 1 : length(t)){
  #t[r] <- which(indicator[,which(accuracy==tail(sort(accuracy),10))[i]]==extreme_indicator[r,which(accuracy==tail(sort(accuracy),10))[i]])
  #}
  #points(t,extreme_indicator[,which(accuracy==tail(sort(accuracy),10))[i]],col='red',pch=19)
  #points(length(deviation_table[,1]),indicator[length(deviation_table[,1]),which(accuracy==tail(sort(accuracy),10))[i]],col='blue',pch=19)
  #abline(a = NULL, b = NULL, h = NULL, v = a1, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = NULL, v = a2, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = NULL, v = b1, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = NULL, v = b2, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = NULL, v = c1, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = NULL, v = c2, reg = NULL,coef = NULL, untf = FALSE,col='green')
  #abline(a = NULL, b = NULL, h = max(extreme_indicator[,which(accuracy==tail(sort(accuracy),10))[i]]), reg = NULL,coef = NULL, untf = FALSE,col='yellow')
  #xx=seq(1,length(deviation_table[,1]),13);  
  #labb=as.vector(deviation_table[,1])
  #axis(1,at=xx,label=labb[xx],padj=TRUE)
  #par(new=TRUE)
  #plot(SPX[z:length(SPX[,2]),2],type="l",col="blue",xaxt="n",yaxt="n",xlab="",ylab="",lwd=3)
  #axis(4)
  #mtext("S&P500",side=4,line=3)
#}

#weighting choices with top 10 accuracy
top_10_weight <- which(accuracy==tail(sort(accuracy),10))

#using the average weight
average_indicator <-  rep(0,length(deviation_table[,2]))
for (i in 1:length(deviation_table[,2])){
  average_indicator[i] <- mean(indicator[i,top_10_weight])
}

#plot average weighted indicator
plot(average_indicator,type='l',,ylab='Deviation',xaxt='n',main='Leading Indicators Average Index',lwd=3)
extreme_points <- head(sort(average_indicator),pct*length(average_indicator))
r <- rep(0,length(extreme_points))
for (i in 1:length(extreme_points)){
  r[i] <- which(average_indicator==extreme_points[i])
}
points(r,extreme_points,col='red',pch=19)
points(length(average_indicator),tail(average_indicator,1),col='blue',pch=19)
abline(a = NULL, b = NULL, h = NULL, v = a1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = a2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = b1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = b2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = c1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = c2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = tail(sort(extreme_points),1),v = NULL,reg = NULL,coef = NULL, untf = FALSE,col='yellow')
xx=seq(1,length(deviation_table[,1]),13) 
labb=as.vector(deviation_table[,1])
axis(1,at=xx,label=labb[xx],padj=TRUE)
par(new=TRUE)
plot(SPX[z:length(SPX[,2]),2],type="l",col="blue",xaxt="n",yaxt="n",xlab="",ylab="",lwd=3)
axis(4)
mtext("S&P500",side=4,line=3)


#smooth line using triangle algorithm 
#(a-2)Triangle Smooth, All Limit Point
avg_day <- 5;#odd number
avg_day_win <- floor(avg_day/2);
avg_p_len <- length(average_indicator);
smooth_indicator <- vector(length=avg_p_len);
smooth_indicator[1:avg_day_win]<- average_indicator[1:avg_day_win]
for(i in (avg_day_win+1):(avg_p_len-avg_day_win))
{
  for(j in 1:avg_day)
  {
    smooth_indicator[i]=smooth_indicator[i]+(avg_day_win+1-abs(j-avg_day_win-1))*(average_indicator[i+j-avg_day_win-1])^3;
  }
  smooth_indicator[i]=(smooth_indicator[i]/((1+avg_day_win)^2))^1/3;
}
smooth_indicator[(avg_p_len-avg_day_win+1):avg_p_len]=average_indicator[(avg_p_len-avg_day_win+1):avg_p_len];
##
jpeg('testpmpm.jpg')
##
plot(smooth_indicator,type='l',lwd=3,,xaxt='n',ylab='Deviation',xlab='Date',main='Smooth Indicator')
points(r,smooth_indicator[r],col='red',pch=19)
points(length(smooth_indicator),tail(smooth_indicator,1),col='blue',pch=19)
abline(a = NULL, b = NULL, h = NULL, v = a1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = a2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = b1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = b2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = c1, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = NULL, v = c2, reg = NULL,coef = NULL, untf = FALSE,col='green')
abline(a = NULL, b = NULL, h = max(smooth_indicator[r]),v = NULL,reg = NULL,coef = NULL, untf = FALSE,col='yellow')
xx=seq(1,length(deviation_table[,1]),13) 
labb=as.vector(deviation_table[,1])
axis(1,at=xx,label=labb[xx],padj=TRUE)
par(new=TRUE)
plot(SPX[z:length(SPX[,2]),2],type="l",col="blue",xaxt="n",yaxt="n",xlab="",ylab="",lwd=3)
axis(4)
mtext("S&P500",side=4,line=3)
dev.off()
return (10)
}
