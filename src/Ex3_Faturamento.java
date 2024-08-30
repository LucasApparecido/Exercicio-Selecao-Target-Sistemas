import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.text.DecimalFormat;

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

        for (int i = 1; i <= 30; i++) {
            JSONObject dailyRevenue = new JSONObject();
            double value = random.nextDouble() * 1000;
            dailyRevenue.put("dia", i);
            dailyRevenue.put("valor", value);
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

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static void calculateRevenue(JSONArray revenues) {
        double minRevenue = Double.MAX_VALUE;
        double maxRevenue = Double.MIN_VALUE;
        double totalRevenue = 0;
        int daysWithRevenue = 0;

        for (int i = 0; i < revenues.length(); i++) {
            double value = revenues.getJSONObject(i).getDouble("valor");
            if (value > 0) {
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
            double value = revenues.getJSONObject(i).getDouble("valor");
            if (value > averageRevenue) {
                daysAboveAverage++;
            }
        }

        System.out.println("Menor valor de faturamento: R$" + df.format(minRevenue));
        System.out.println("Maior valor de faturamento: R$" + df.format(maxRevenue));
        System.out.println("Número de dias com faturamento acima da média: " + daysAboveAverage);
    }
}
