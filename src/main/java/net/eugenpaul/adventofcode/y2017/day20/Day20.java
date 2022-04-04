package net.eugenpaul.adventofcode.y2017.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;
import net.eugenpaul.adventofcode.y2017.day20.Particle.V3;

public class Day20 extends SolutionTemplate {

    @Getter
    private int slowestNumber;
    @Getter
    private int particlesLeft;

    public static void main(String[] args) {
        Day20 puzzle = new Day20();
        puzzle.doPuzzleFromFile("y2017/day20/puzzle1.txt");
    }

    @Override
    public boolean doEvent(List<String> eventData) {
        if (null == eventData) {
            return false;
        }

        AtomicInteger number = new AtomicInteger(0);
        List<Particle> particles = eventData.stream()//
                .map(v -> Particle.fromString(v, number.getAndIncrement()))//
                .collect(Collectors.toList());

        slowestNumber = doPuzzle1(particles);
        particlesLeft = doPuzzle2(particles);

        logger.log(Level.INFO, () -> "slowestNumber : " + getSlowestNumber());
        logger.log(Level.INFO, () -> "particlesLeft : " + getParticlesLeft());

        return true;
    }

    private int doPuzzle2(List<Particle> particlesInput) {
        List<Particle> particles = new ArrayList<>(particlesInput);
        int steps = 100; // are 100 Round enough?
        while (steps > 0) {
            doStep(particles);
            particles = particles.stream()//
                    .sorted((a, b) -> (int) (a.getPosition().abs() - b.getPosition().abs()))//
                    .collect(Collectors.toList());

            filterCoolision(particles);
            steps--;
        }

        return particles.size();
    }

    private void filterCoolision(List<Particle> particles) {
        var iterator = particles.listIterator();
        Map<V3, Integer> collisionMap = new HashMap<>();
        while (iterator.hasNext()) {
            var element = iterator.next();
            collisionMap.compute(element.getPosition(), (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return v + 1;
            });
        }

        iterator = particles.listIterator();
        while (iterator.hasNext()) {
            var element = iterator.next();
            if (collisionMap.get(element.getPosition()) > 1) {
                iterator.remove();
            }
        }
    }

    private void doStep(List<Particle> particles) {
        for (Particle particle : particles) {
            particle.getVelocity().setX(particle.getVelocity().getX() + particle.getAcceleration().getX());
            particle.getVelocity().setY(particle.getVelocity().getY() + particle.getAcceleration().getY());
            particle.getVelocity().setZ(particle.getVelocity().getZ() + particle.getAcceleration().getZ());

            particle.getPosition().setX(particle.getPosition().getX() + particle.getVelocity().getX());
            particle.getPosition().setY(particle.getPosition().getY() + particle.getVelocity().getY());
            particle.getPosition().setZ(particle.getPosition().getZ() + particle.getVelocity().getZ());
        }
    }

    private int doPuzzle1(List<Particle> particles) {
        // in the long term the slowest Particle will stay closest to position <0,0,0>
        List<Particle> sortedParticles = particles.stream()//
                .sorted((a, b) -> {
                    long ac = a.getAcceleration().abs() - b.getAcceleration().abs();
                    if (ac != 0) {
                        return compLong(ac);
                    }
                    // if two Particles have same Acceleration then the slowest stay closest to position <0,0,0>
                    ac = a.getVelocity().abs() - b.getVelocity().abs();
                    if (ac != 0) {
                        return compLong(ac);
                    }
                    // if two Particles have same Acceleration and same Velocity then the best start Position stay closest to position <0,0,0>
                    // It is not 100% right. Example:
                    // p=<20,0,0>, v=< -2,0,0>, a=<0,0,0>
                    // p=< 4,0,0>, v=< 2,0,0>, a=<0,0,0>
                    // Particle 1 have same Acceleration, same Velocity, better start Position but it will be not stay closest to position <0,0,0>
                    ac = a.getPosition().abs() - b.getPosition().abs();
                    return compLong(ac);
                })//
                .collect(Collectors.toList());

        return sortedParticles.get(0).getNumber();
    }

    private static int compLong(long comp) {
        if (comp > 0L) {
            return 1;
        }
        if (comp < 0L) {
            return -1;
        }
        return 0;
    }

}
