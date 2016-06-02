macro <- function(x,y,z){
  setwd('/Users/Haixin/Documents/Eclipse/PlotFinancialConditionIndex')
  q <- read.csv('output.csv')
  simple_result1 <- q[,2]*x
  write.csv(simple_result1,file='q1.csv')
  simple_result2 <- q[,3]*y
  write.csv(simple_result2,file='q2.csv')
  simple_result3 <- q[,4]*z
  write.csv(simple_result3,file='q3.csv')
  return(10) 
  }

