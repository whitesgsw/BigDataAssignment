//code block takes features containing outliers and filters main dataframe against them
//code will generate a subset of main dataset without outliers.
val clean_df = creditriskdf
//input feature list conatining features with outliers persisting
for (feature <- feat_list){
    //summarise column and query the DataFrame output
    var firstQ = creditRiskdf.select(feature).summary().where($"summary" === "25%").select(feature).first().mkString.toFloat
    var thirdQ = creditRiskdf.select(feature).summary().where($"summary" === "75%").select(feature).first().mkString.toFloat
    //use formulas below to test threshold of outliers
    var testValHigh = thirdQ + (1.5 * thirdQ - firstQ)
    var testValLow = firstQ - (1.5 * thirdQ - firstQ)

    val clean_df = clean_df.filter(feature < lit(testValHigh))
    val clean_df = clean_df.filter(feature > lit(testValLow))
}
