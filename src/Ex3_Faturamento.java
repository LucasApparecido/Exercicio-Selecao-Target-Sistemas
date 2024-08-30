import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

public class Ex3_Faturamento {

    public static void main(String[] args) {
        String filePath = "faturamento.json";

        generateJsonFile(filePath);

        try {
            JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(filePath))));
            JSONArray revenues = jsonObject.getJSONArray("faturamento");

            calculateRevenue(revenues);

        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo Json: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro no processamento do Json: " + e.getMessage());
        }
    }

    private static void generateJsonFile(String filePath) {
        JSONArray revenues = new JSONArray();
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();

        for (int i = 1; i <= 30; i++) {
            JSONObject dailyRevenue = new JSONObject();
            double value = random.nextDouble() * 1000;
            calendar.set(Calendar.DAY_OF_MONTH, i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String dayName = getDayName(dayOfWeek);

            dailyRevenue.put("dia", i);
            dailyRevenue.put("valor", value);
            dailyRevenue.put("diaDaSemana", dayName);
            revenues.put(dailyRevenue);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("faturamento", revenues);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString(4));
            System.out.println("O arquivo Json foi gerado com sucesso!: " + filePath);
        } catch (IOException e) {
            System.err.println("Houve um erro na geração do arquivo Json: " + e.getMessage());
        }
    }

    private static String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "Domingo";
            case Calendar.MONDAY: return "Segunda-feira";
            case Calendar.TUESDAY: return "Terça-feira";
            case Calendar.WEDNESDAY: return "Quarta-feira";
            case Calendar.THURSDAY: return "Quinta-feira";
            case Calendar.FRIDAY: return "Sexta-feira";
            case Calendar.SATURDAY: return "Sábado";
            default: return "";
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static void calculateRevenue(JSONArray revenues) {
        double minRevenue = Double.MAX_VALUE;
        double maxRevenue = Double.MIN_VALUE;
        double totalRevenue = 0;
        int daysWithRevenue = 0;

        for (int i = 0; i < revenues.length(); i++) {
            JSONObject dailyRevenue = revenues.getJSONObject(i);
            double value = dailyRevenue.getDouble("valor");
            String dayOfWeek = dailyRevenue.getString("diaDaSemana");

            if (value > 0 && !dayOfWeek.equals("Sábado") && !dayOfWeek.equals("Domingo")) {
                totalRevenue += value;
                daysWithRevenue++;
                if (value < minRevenue) {
                    minRevenue = value;
                }
                if (value > maxRevenue) {
                    maxRevenue = value;
                }
            }
        }

        double averageRevenue = daysWithRevenue > 0 ? totalRevenue / daysWithRevenue : 0;

        int daysAboveAverage = 0;
        for (int i = 0; i < revenues.length(); i++) {
            JSONObject dailyRevenue = revenues.getJSONObject(i);
            double value = dailyRevenue.getDouble("valor");
            String dayOfWeek = dailyRevenue.getString("diaDaSemana");

            if (value > averageRevenue && !dayOfWeek.equals("Sábado") && !dayOfWeek.equals("Domingo")) {
                daysAboveAverage++;
            }
        }

        System.out.println("Menor valor de faturamento: R$" + df.format(minRevenue));
        System.out.println("Maior valor de faturamento: R$" + df.format(maxRevenue));
        System.out.println("Número de dias com faturamento acima da média: " + daysAboveAverage);
    }
}
