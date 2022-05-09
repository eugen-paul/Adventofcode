package net.eugenpaul.adventofcode.helper;

import lombok.Getter;

public enum DevicesOpcodes {
    ADDR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] + responseegister[(int) instruction[2]];
            return responseegister;
        }
    }, //
    ADDI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] + instruction[2];
            return responseegister;
        }
    }, //
    MULR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] * responseegister[(int) instruction[2]];
            return responseegister;
        }
    }, //
    MULI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] * instruction[2];
            return responseegister;
        }
    }, //
    BANR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] & responseegister[(int) instruction[2]];
            return responseegister;
        }
    }, //
    BANI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] & instruction[2];
            return responseegister;
        }
    }, //
    BORR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] | responseegister[(int) instruction[2]];
            return responseegister;
        }
    }, //
    BORI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]] | instruction[2];
            return responseegister;
        }
    }, //
    SETR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = responseegister[(int) instruction[1]];
            return responseegister;
        }
    }, //
    SETI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            responseegister[(int) instruction[3]] = instruction[1];
            return responseegister;
        }
    }, //
    GTIR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (instruction[1] > responseegister[(int) instruction[2]]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    GTRI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (responseegister[(int) instruction[1]] > instruction[2]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    GTRR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (responseegister[(int) instruction[1]] > responseegister[(int) instruction[2]]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    EQIR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (instruction[1] == responseegister[(int) instruction[2]]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    EQRI {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (responseegister[(int) instruction[1]] == instruction[2]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    EQRR {
        @Override
        public long[] execute(long[] register, long[] instruction) {
            long[] responseegister = register.clone();
            if (responseegister[(int) instruction[1]] == responseegister[(int) instruction[2]]) {
                responseegister[(int) instruction[3]] = 1;
            } else {
                responseegister[(int) instruction[3]] = 0;
            }
            return responseegister;
        }
    }, //
    ;

    @Getter
    private final String opcode;

    private DevicesOpcodes() {
        this.opcode = this.toString().toLowerCase();
    }

    public static DevicesOpcodes fromString(String opcodeString) {
        for (DevicesOpcodes o : DevicesOpcodes.values()) {
            if (opcodeString.equals(o.opcode)) {
                return o;
            }
        }

        throw new IllegalArgumentException("wrong opcode: " + opcodeString);
    }

    public static long[] execute(String instruction, long[] register) {
        String[] elements = instruction.split(" ");
        DevicesOpcodes o = fromString(elements[0]);
        int from = Integer.parseInt(elements[1]);
        int from1 = Integer.parseInt(elements[2]);
        int to = Integer.parseInt(elements[3]);

        long[] inst = { 0, from, from1, to };
        return o.execute(register, inst);
    }

    public abstract long[] execute(long[] register, long[] instruction);
}