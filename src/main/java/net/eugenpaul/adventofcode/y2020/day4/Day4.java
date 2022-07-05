package net.eugenpaul.adventofcode.y2020.day4;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day4 extends SolutionTemplate {

    private class PasswordData {
        private Integer byr = null;
        private Integer iyr = null;
        private Integer eyr = null;
        private String hgt = null;
        private String hcl = null;
        private String ecl = null;
        private String pid = null;

        private boolean isValid() {
            return byr != null //
                    && iyr != null //
                    && eyr != null //
                    && hgt != null //
                    && hcl != null //
                    && ecl != null //
                    && pid != null //
            ;
        }

        private boolean isValid2() {
            return isByrValid() //
                    && isIyrValid() //
                    && isEyrValid() //
                    && isHgtValid() //
                    && isHclValid() //
                    && isEclValid() //
                    && isPidValid() //
            ;
        }

        private boolean isByrValid() {
            return byr != null && byr >= 1920 && byr <= 2002;
        }

        private boolean isIyrValid() {
            return iyr != null && iyr >= 2010 && iyr <= 2020;
        }

        private boolean isEyrValid() {
            return eyr != null && eyr >= 2020 && eyr <= 2030;
        }

        private boolean isHgtValid() {
            if (hgt == null || !(hgt.endsWith("cm") || hgt.endsWith("in"))) {
                return false;
            }

            try {
                int height = Integer.parseInt(hgt.substring(0, hgt.length() - 2));

                if (hgt.endsWith("cm") && height >= 150 && height <= 193) {
                    return true;
                }

                return hgt.endsWith("in") && height >= 59 && height <= 76;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean isHclValid() {
            if (hcl == null) {
                return false;
            }

            return hcl.matches("#[0-9a-f]{6}");
        }

        private boolean isEclValid() {
            return ecl != null //
                    && (ecl.equals("amb") //
                            || ecl.equals("blu") //
                            || ecl.equals("brn") //
                            || ecl.equals("gry") //
                            || ecl.equals("grn") //
                            || ecl.equals("hzl") //
                            || ecl.equals("oth") //
                    );
        }

        private boolean isPidValid() {
            return pid != null && pid.length() == 9 //
                    && pid.matches("\\d*");
        }
    }

    @Getter
    private long validPasswords;
    @Getter
    private long validPasswords2;

    public static void main(String[] args) {
        Day4 puzzle = new Day4();
        puzzle.doPuzzleFromFile("y2020/day4/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {

        List<PasswordData> passwords = readPassDatas(eventData);

        validPasswords = passwords.stream().filter(PasswordData::isValid).count();
        validPasswords2 = passwords.stream().filter(PasswordData::isValid2).count();

        logger.log(Level.INFO, () -> "validPasswords     : " + getValidPasswords());
        logger.log(Level.INFO, () -> "validPasswords2    : " + getValidPasswords2());

        return true;
    }

    private List<PasswordData> readPassDatas(List<String> eventData) {
        List<PasswordData> response = new LinkedList<>();
        PasswordData pass = new PasswordData();
        response.add(pass);

        for (String data : eventData) {
            if (data.isEmpty()) {
                pass = new PasswordData();
                response.add(pass);
            } else {
                addToPass(pass, data);
            }
        }

        return response;
    }

    private void addToPass(PasswordData pass, String data) {
        for (String element : data.split(" ")) {
            String[] params = element.split(":");
            switch (params[0]) {
            case "byr":
                pass.byr = Integer.parseInt(params[1]);
                break;
            case "iyr":
                pass.iyr = Integer.parseInt(params[1]);
                break;
            case "eyr":
                pass.eyr = Integer.parseInt(params[1]);
                break;
            case "hgt":
                pass.hgt = params[1];
                break;
            case "hcl":
                pass.hcl = params[1];
                break;
            case "ecl":
                pass.ecl = params[1];
                break;
            case "pid":
                pass.pid = params[1];
                break;
            default:
                break;
            }
        }
    }

}
