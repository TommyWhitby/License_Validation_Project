import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validator {
    private int born;
    private int issued;
    private int expires;
    private String height;
    private String hair;
    private String eyes;
    private int usmca;
    private String state;

    public Validator(int born, int issued, int expires, String height, String hair, String eyes, int usmca, String state) {
        this.born = born;
        this.issued = issued;
        this.expires = expires;
        this.height = height;
        this.hair = hair;
        this.eyes = eyes;
        this.usmca = usmca;
        this.state = state;
    }

    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get("data/data_test2.bat"))) { //change the data/... to use different test files
            Arrays.stream(lines.collect(Collectors.joining("\n"))
                .split("\n{2}"))
                .map(entry -> Arrays.stream(entry.split("\\s+"))
                    .map(field -> field.split(":"))
                    .collect(Collectors.toMap(
                        parts -> parts[0],
                        parts -> parts[1],
                        (v1, v2) -> v1,
                        HashMap::new
                )))
                // check for mandatory fields
                .filter(map -> map.keySet().containsAll(Arrays.asList(
                    "born", "issued", "expires", "height", "hair", "eyes", "usmca")))
                // age
                .filter(map -> LocalDate.now().getYear() - Integer.parseInt(map.get("born")) >= 21)
                // issued
                .filter(map -> {
                    return (Integer.parseInt(map.get("issued"))) <= (LocalDate.now().getYear()) && (LocalDate.now().getYear()) - (Integer.parseInt(map.get("issued"))) <= 10;
                })
                // expiry
                .filter(map -> {
                    return (Integer.parseInt(map.get("expires"))) >= (LocalDate.now().getYear()) && (Integer.parseInt(map.get("expires"))) <= (LocalDate.now().getYear()) + 10;
                })
                // height
                .filter(map -> {
                    if ((map.get("height")).endsWith("cm")) {
                        return (Integer.parseInt((map.get("height")).substring(0, (map.get("height")).length() - 2))) >= 150 && (Integer.parseInt((map.get("height")).substring(0, (map.get("height")).length() - 2))) <= 193;
                    } else if ((map.get("height")).endsWith("in")) {
                        return (Integer.parseInt((map.get("height")).substring(0, (map.get("height")).length() - 2))) >= 59 && (Integer.parseInt((map.get("height")).substring(0, (map.get("height")).length() - 2))) <= 76;
                    }
                    return false;
                })
                // hair color
                .filter(map -> map.get("hair").matches("^#[0-9A-Fa-f]{6}$"))
                // eye color
                .filter(map -> Stream.of("amber", "blue", "brown", "gray", "green", "hazel", "other")
                    .anyMatch(color -> color.equals(map.get("eyes").toLowerCase())))
                // usmca
                .filter(map -> {
                    return (Integer.parseInt(map.get("usmca"))) >= 100000000 && (Integer.parseInt(map.get("usmca"))) <= 999999999;
                })
                .map(map -> new Validator(
                    Integer.parseInt(map.getOrDefault("born", "0")),
                    Integer.parseInt(map.getOrDefault("issued", "0")),
                    Integer.parseInt(map.getOrDefault("expires", "0")),
                    map.getOrDefault("height", "NULL"),
                    map.getOrDefault("hair", "NULL"),
                    map.getOrDefault("eyes", "NULL"),
                    Integer.parseInt(map.getOrDefault("usmca", "0")),
                    map.getOrDefault("state", "NULL")
                ))
                .map(pass -> Stream.of(
                    pass.born != 0 ? "born=" + pass.born : null,
                    pass.issued != 0 ? "issued=" + pass.issued : null,
                    pass.expires != 0 ? "expires=" + pass.expires : null,
                    !pass.height.equals("NULL") ? "height=" + pass.height : null,
                    !pass.hair.equals("NULL") ? "hair=" + pass.hair : null,
                    !pass.eyes.equals("NULL") ? "eyes=" + pass.eyes : null,
                    pass.usmca != 0 ? "usmca=" + pass.usmca : null,
                    !pass.state.equals("NULL") ? "state=" + pass.state : null
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", ", "{", "}")))
                .collect(Collector.of(
                () -> new int[]{0},
                (count, passString) -> {
                    System.out.println("-".repeat(132));
                    System.out.println(passString);
                    count[0]++;
                },
                (count1, count2) -> {
                    count1[0] += count2[0];
                    return count1;
                },
                count -> {
                    System.out.println("=".repeat(132));
                    System.out.println("Valid records: " + count[0]);
                    return count[0];
                }
            ));
        }
    }
}