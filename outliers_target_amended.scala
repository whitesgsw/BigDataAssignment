//write function to check if there are any outliers in a given domain and show boxplot [Sean]
//slice a small test list to ensure functionality
//var test_lis = creditRiskFeatures.slice(0,2) //TEST var for codeblock
var feat_list = List() //empty list to append features containing outliers in
for (feature <- creditRiskFeatures) {
    //summarise column and query the DataFrame output
    var firstQ = creditRiskdf.select(feature).summary().where($"summary" === "25%").select(feature).first().mkString.toFloat
    var thirdQ = creditRiskdf.select(feature).summary().where($"summary" === "75%").select(feature).first().mkString.toFloat
    //use formulas below to test threshold of outliers
    var testValHigh = thirdQ + (1.5 * thirdQ - firstQ)
    var testValLow = firstQ - (1.5 * thirdQ - firstQ)
    //check to see if thresholds are exceeded in the column and count
    var outHigh = creditRiskdf.filter(col(feature) > lit(testValHigh)).count()
    var outLow = creditRiskdf.filter(col(feature) < lit(testValLow)).count()

    //notify us whether or not column contains outliers
    if (outHigh > 0 || outLow > 0 ){
        println(feature.concat(": Contains outliers, #").concat((outHigh + outLow).toString))
        var feat_list = List.concat(feat_list, feature)
    }else if (outHigh = 0 || outLow = 0){
        println(feature.concat(": Does not contain outliers"))
    }
}
