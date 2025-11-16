package net.eugenpaul.adventofcode.helper;

import java.util.ArrayList;
import java.util.List;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Helper class for Matheclipse operations<br>
 * online: https://symjaweb.appspot.com/
 */
public class MatheclipseHelper {

    private List<String> equations;
    private List<String> unknowns;

    private IExpr result;

    /**
     * Add equation to the equation system<br>
     * Example: "2x + 3y == 8"
     * 
     * @param equation
     */
    public void addEquation(String equation) {
        if (equations == null) {
            equations = new ArrayList<>();
        }
        equations.add(equation);
    }

    /**
     * Add unknown variable to solve for<br>
     * Example: "x"
     */
    public void addUnknown(String unknown) {
        if (unknowns == null) {
            unknowns = new ArrayList<>();
        }
        unknowns.add(unknown);
    }

    /**
     * Löst Gleichungssysteme symbolisch. Antwort köntne sein: x = 1/2*(71530-10*Sqrt(51203017))<br>
     * Vorbereitung fuer den Aufruf:
     * <ul>
     * <li>Gleichungen mit addEquation(String equation) hinzufuegen (entwender einzeln oder als Liste (kommagetrennt))
     * <li>Unbekannte mit addUnknown(String unknown) hinzufuegen (entwender einzeln oder als Liste (kommagetrennt))
     * </ul>
     * 
     * Loest Gleichungssysteme symbolisch Solve({2x + 3y == 8, 5x + 4y == 14}, {x, y})<br>
     * Ergebnis: {{x->2, y->1}}
     */
    public void solve() {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Solve({");
        sb.append(String.join(", ", equations));
        sb.append("}, {");
        sb.append(String.join(", ", unknowns));
        sb.append("})");
        result = evaluator.eval(sb.toString());
    }

    /**
     * Numerische Lösung. Antwort könnte sein: x -> ±1.41421<br>
     * Vorbereitung fuer den Aufruf:
     * <ul>
     * <li>Gleichungen mit addEquation(String equation) hinzufuegen (entwender einzeln oder als Liste (kommagetrennt))
     * <li>Unbekannte mit addUnknown(String unknown) hinzufuegen (entwender einzeln oder als Liste (kommagetrennt))
     * </ul>
     * 
     * Loest Gleichungssysteme numerische NSolve (x^2 == 2, x) → {x -> ±1.41421}<br>
     * Ergebnis: {{x->2, y->1}}
     */
    public void nSolve() {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("NSolve({");
        sb.append(String.join(", ", equations));
        sb.append("}, {");
        sb.append(String.join(", ", unknowns));
        sb.append("})");
        result = evaluator.eval(sb.toString());
    }

    /**
     * Gibt den ersten Wert der Unbekannten zurueck. Zuvor muss solve oder nsolve aufgerufen werden<br>
     * Beispiel: getResult("x") -> 2
     * 
     * @param unknown Name der Unbekannten
     * @return Wert der Unbekannten
     */
    public Long getResult(String unknown) {
        if (result == null) {
            throw new IllegalStateException("No result available. Call solve() first.");
        }
        String resultStr = result.toString();
        String searchStr = unknown + "->";
        int index = resultStr.indexOf(searchStr);
        if (index == -1) {
            throw new IllegalArgumentException("Unknown variable not found in result: " + unknown);
        }
        int startIndex = index + searchStr.length();
        int endIndex1 = resultStr.indexOf(",", startIndex);
        if (endIndex1 == -1) {
            endIndex1 = Integer.MAX_VALUE;
        }
        int endIndex2 = resultStr.indexOf("}", startIndex);
        if (endIndex2 == -1) {
            endIndex2 = Integer.MAX_VALUE;
        }
        int endIndex = Math.min(endIndex1, endIndex2);
        String valueStr = resultStr.substring(startIndex, endIndex).trim();
        return Long.parseLong(valueStr);
    }

    /**
     * Gibt den alle Werte der Unbekannten zurueck. Zuvor muss solve oder nsolve aufgerufen werden<br>
     * Beispiel: getResult("x") -> 2
     * 
     * @param unknown Name der Unbekannten
     * @return Werte der Unbekannten
     */
    public List<String> getResults(String unknown) {
        if (result == null) {
            throw new IllegalStateException("No result available. Call solve() first.");
        }
        List<String> response = new ArrayList<>();
        int startPos = 0;
        while (true) {
            String resultStr = result.toString();
            String searchStr = unknown + "->";
            int index = resultStr.indexOf(searchStr, startPos);
            if (index == -1) {
                break;
            }
            int startIndex = index + searchStr.length();
            int endIndex1 = resultStr.indexOf(",", startIndex);
            if (endIndex1 == -1) {
                endIndex1 = Integer.MAX_VALUE;
            }
            int endIndex2 = resultStr.indexOf("}", startIndex);
            if (endIndex2 == -1) {
                endIndex2 = Integer.MAX_VALUE;
            }
            int endIndex = Math.min(endIndex1, endIndex2);
            String valueStr = resultStr.substring(startIndex, endIndex).trim();
            response.add(valueStr);
            startPos = endIndex;
        }
        return response;
    }

    /**
     * Gibt das rohe Ergebnis der Gleichung zurueck. Zuvor muss solve aufgerufen werden
     * 
     * @return Roher Ergebnisstring
     */
    public String getRawResult() {
        if (result == null) {
            throw new IllegalStateException("No result available. Call solve() first.");
        }
        return result.toString();
    }

    /**
     * Vorbereitung fuer den Aufruf:
     * <ul>
     * <li>Gleichungen mit addEquation(String equation) hinzufuegen (entweder einzeln oder als Liste (kommagetrennt))
     * </ul>
     * 
     * Vereinfacht algebraische Ausdrücke Simplify((x^2 - 1)/(x - 1)) → x + 1<br>
     * 
     * @return Vereinfachter Ausdruck
     */
    public String simplify() {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Simplify({");
        sb.append(String.join(", ", equations));
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Vereinfacht algebraische Ausdrücke Simplify((x^2 - 1)/(x - 1)) → x + 1<br>
     * 
     * @param equation
     * @return
     */
    public static String simplify(String equation) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Simplify({");
        sb.append(equation);
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Expandiert algebraische Ausdrücke Expand((x + 1)^2) → x^2 + 2x + 1<br>
     * 
     * @param equation
     * @return
     */
    public static String expand(String equation) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Expand({");
        sb.append(equation);
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Faktorisieren algebraische Ausdrücke Factor(x^2 - 1) → (x - 1)(x + 1)<br>
     * 
     * @param equation
     * @return
     */
    public static String factor(String equation) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Factor({");
        sb.append(equation);
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Berechnet die Ableitung eines Ausdrucks D(x^2 + 2x + 1, x) → 2x + 2<br>
     * 
     * @param equation
     * @param variable
     * @return
     */
    public static String derivative(String equation, String variable) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("D(");
        sb.append(equation);
        sb.append(", ");
        sb.append(variable);
        sb.append(")");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Loest Gleichungssysteme symbolisch Solve({2x + 3y == 8, 5x + 4y == 14}, {x, y})<br>
     * Ergebnis: {{x->2, y->1}}
     * 
     * @param equations Gleichungen (kommagetrennt)
     * @param unknowns  Unbekannte (kommagetrennt)
     * @return Roher Ergebnisstring
     */
    public static String solve(String equations, String unknowns) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Solve({");
        sb.append(equations);
        sb.append("}, {");
        sb.append(unknowns);
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Loest Gleichungssysteme numerisch NSolve(x^2 == 2, x)<br>
     * Ergebnis: {x -> ±1.41421}
     * 
     * @param equations Gleichungen (kommagetrennt)
     * @param unknowns  Unbekannte (kommagetrennt)
     * @return Roher Ergebnisstring
     */
    public static String nSolve(String equations, String unknowns) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("NSolve({");
        sb.append(equations);
        sb.append("}, {");
        sb.append(unknowns);
        sb.append("})");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Loest und vereinfacht Bedingungen (x^2 > 4, x) → x < -2 ∨ x > 2<br>
     * 
     * @param equation
     * @param variable
     * @return Vereinfachter Ausdruck
     */
    public static String reduce(String equation, String variable) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("Reduce(");
        sb.append(equation);
        sb.append(", ");
        sb.append(variable);
        sb.append(")");
        IExpr evalResult = evaluator.eval(sb.toString());
        return evalResult.toString();
    }

    /**
     * Numerische Auswertung: N(Pi) → 3.14159<br>
     * 
     * @param equation
     * @param variable
     * @return Numerische Auswertung
     */
    public static double numerischD(String equation) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("N(");
        sb.append(equation);
        sb.append(")");
        IExpr evalResult = evaluator.eval(sb.toString());
        return Double.parseDouble(evalResult.toString());
    }

    /**
     * Numerische Auswertung: N(Pi) → 3.14159<br>
     * 
     * @param equation
     * @param variable
     * @return Numerische Auswertung
     */
    public static Long numerischL(String equation) {
        ExprEvaluator evaluator = new ExprEvaluator();
        StringBuilder sb = new StringBuilder();
        sb.append("N(");
        sb.append(equation);
        sb.append(")");
        IExpr evalResult = evaluator.eval(sb.toString());
        return Double.valueOf(evalResult.toString()).longValue();
    }
}
