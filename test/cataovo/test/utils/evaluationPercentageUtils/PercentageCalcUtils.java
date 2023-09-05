package cataovo.test.utils.evaluationPercentageUtils;


import cataovo.controllers.implement.EvaluationProcessorControllerImplements;
import cataovo.utils.evaluationUtils.EvaluationCalcType;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Bianca Leopoldo Ramos
 */
public class PercentageCalcUtils {
    
    public static void main(String[] args) {
       cataovo.utils.evaluationUtils.PercentageCalcUtils calcUtils = new cataovo.utils.evaluationUtils.PercentageCalcUtils();
       String result1 = calcUtils.calculate(EvaluationCalcType.BASIC_FORMULA, 3f, 3f, null, null);
        
//        EvaluationProcessorControllerImplements controllerImplements = new EvaluationProcessorControllerImplements();
//        float result2 = controllerImplements.getPercentageFalseNegative(3, 3);
       
        System.out.println("Result1: " + result1);
//        System.out.println("Result2: " + result2);
        
        String[] data = "Teste novo".split(";");
        
        System.out.println(data.length);

    }
    
}
