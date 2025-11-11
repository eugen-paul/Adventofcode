package net.eugenpaul.adventofcode.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Area {
    private int x;
    private int y;

    private int width;
    private int height;

    public Area(SimplePos a, SimplePos b) {
        x = a.getX();
        y = a.getY();
        width = b.getX() - a.getX() + 1;
        height = b.getY() - a.getY() + 1;
    }

    public static Area fromPP(SimplePos a, SimplePos b) {
        return new Area(a, b);
    }

    /**
     * Erstellt die Area und sorgt dafuer, das (x,y) oben Links ist und width nach rechts und height nach unten zeigt.
     * 
     * @param a
     * @param b
     * @return
     */
    public static Area fromPPOpt(SimplePos a, SimplePos b) {
        int minX = Math.min(a.getX(), b.getX());
        int maxX = Math.max(a.getX(), b.getX());
        int minY = Math.min(a.getY(), b.getY());
        int maxY = Math.max(a.getY(), b.getY());
        return new Area(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    /**
     * Liefert True zurueck, wenn due Flaeche nur ein Punkt ist (Breite und Hoehe gleich 1)
     * 
     * @return
     */
    public boolean istEinPunkt() {
        return width == 1 && height == 1;
    }

    public SimplePos getPos() {
        return new SimplePos(x, y);
    }

    public SimplePos getPosUpLeft() {
        return getPos();
    }

    public SimplePos getPosUpRight() {
        return new SimplePos(x + width - 1, y);
    }

    public SimplePos getPosDownLeft() {
        return new SimplePos(x, y + height - 1);
    }

    public SimplePos getPosDownRight() {
        return new SimplePos(x + width - 1, y + height - 1);
    }

    public void forEach(BiConsumer<Integer, Integer> consumer) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                consumer.accept(xPos, yPos);
            }
        }
    }

    public void forEach(BiConsumer<Integer, Integer> consumer, BooleanSupplier breakSupplier) {
        for (int xPos = x; xPos < width + x; xPos++) {
            for (int yPos = y; yPos < height + y; yPos++) {
                consumer.accept(xPos, yPos);
                if (breakSupplier.getAsBoolean()) {
                    return;
                }
            }
        }
    }

    public Area copy() {
        return new Area(x, y, width, height);
    }

    /**
     * Teilt die Area in:
     * <ul>
     * <li>4 Stuecke, wenn Hoehe und Breite groesse als 1 sind
     * <li>2 Stuecke, wenn Hoehe oder Breite groesse gleich 1 ist
     * <li>1 Stueck (liefert Kopie zurueck), wenn Hoehe und Breite gleich 1 ist
     * </ul>
     * 
     * @return Liste dermit den Unterteilen
     */
    public List<Area> divide() {
        List<Area> response = new LinkedList<>();

        if (width == 1 && height == 1) {
            response.add(copy());
            return response;
        }

        if (width == 1) {
            int h1 = height / 2;
            response.add(//
                    new Area(//
                            x, //
                            y, //
                            width, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x, //
                            y + h1, //
                            width, //
                            height - h1 //
                    )//
            );
        } else if (height == 1) {
            int w1 = width / 2;
            response.add(//
                    new Area(//
                            x, //
                            y, //
                            w1, //
                            height //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y, //
                            width - w1, //
                            height //
                    )//
            );
        } else {
            int h1 = height / 2;
            int w1 = width / 2;

            response.add(//
                    new Area(//
                            x, //
                            y, //
                            w1, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y, //
                            width - w1, //
                            h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x, //
                            y + h1, //
                            w1, //
                            height - h1 //
                    )//
            );
            response.add(//
                    new Area(//
                            x + w1, //
                            y + h1, //
                            width - w1, //
                            height - h1 //
                    )//
            );
        }

        return response;
    }

    /**
     * Liefert die überlappende Area zweier Areas zurück oder null, falls keine Überlappung besteht.
     *
     * @param a erste Area
     * @param b zweite Area
     * @return überlappende Area oder null
     */
    public static Area overlap(Area a, Area b) {
        int left = Math.max(a.x, b.x);
        int top = Math.max(a.y, b.y);
        int right = Math.min(a.x + a.width - 1, b.x + b.width - 1);
        int bottom = Math.min(a.y + a.height - 1, b.y + b.height - 1);

        if (left > right || top > bottom) {
            return null;
        }
        return new Area(left, top, right - left + 1, bottom - top + 1);
    }

    /**
     * Instanzmethode für Überlappung mit einer anderen Area.
     *
     * @param other andere Area
     * @return überlappende Area oder null
     */
    public Area overlap(Area other) {
        return overlap(this, other);
    }

    /**
     * Prüft, ob diese Area die andere Area komplett beinhaltet (inklusive Ränder).
     *
     * @param other zu prüfende Area
     * @return true, wenn other vollständig innerhalb dieser Area liegt
     */
    public boolean contains(Area other) {
        if (other == null) {
            return false;
        }
        int left1 = this.x;
        int top1 = this.y;
        int right1 = this.x + this.width - 1;
        int bottom1 = this.y + this.height - 1;

        int left2 = other.x;
        int top2 = other.y;
        int right2 = other.x + other.width - 1;
        int bottom2 = other.y + other.height - 1;

        return left1 <= left2 && top1 <= top2 && right1 >= right2 && bottom1 >= bottom2;
    }

    /**
     * Statische Variante: prüft, ob container die inner vollständig beinhaltet.
     */
    public static boolean contains(Area container, Area inner) {
        return container != null && container.contains(inner);
    }

    /**
     * Entfernt die überlappende Area "other" von dieser Area und liefert die Liste der übrig gebliebenen (nicht überlappenden) Areas zurück.
     *
     * Beispiel: Wenn diese Area ein Rechteck ist und other einen Teil davon überdeckt, werden bis zu 4 Teil-Rechtecke zurückgegeben (oben, unten, links,
     * rechts).
     *
     * @param other zu entfernende Area
     * @return Liste der verbleibenden Areas (leer, falls nichts übrig bleibt)
     */
    public List<Area> subtract(Area other) {
        List<Area> result = new LinkedList<>();
        if (other == null) {
            result.add(this.copy());
            return result;
        }

        Area ov = overlap(this, other);
        if (ov == null) { // keine Überlappung -> Original zurückgeben
            result.add(this.copy());
            return result;
        }

        int left = this.x;
        int top = this.y;
        int right = this.x + this.width - 1;
        int bottom = this.y + this.height - 1;

        int ovLeft = ov.x;
        int ovTop = ov.y;
        int ovRight = ov.x + ov.width - 1;
        int ovBottom = ov.y + ov.height - 1;

        // Obere Streifen (über der Überlappung)
        if (ovTop > top) {
            result.add(new Area(left, top, this.width, ovTop - top));
        }

        // Untere Streifen (unter der Überlappung)
        if (ovBottom < bottom) {
            result.add(new Area(left, ovBottom + 1, this.width, bottom - ovBottom));
        }

        // Linke mittlere Fläche (zwischen ovTop..ovBottom links vom Überlappungsbereich)
        if (ovLeft > left) {
            int w = ovLeft - left;
            int h = ovBottom - ovTop + 1;
            result.add(new Area(left, ovTop, w, h));
        }

        // Rechte mittlere Fläche (zwischen ovTop..ovBottom rechts vom Überlappungsbereich)
        if (ovRight < right) {
            int w = right - ovRight;
            int h = ovBottom - ovTop + 1;
            result.add(new Area(ovRight + 1, ovTop, w, h));
        }

        return result;
    }

    public static List<Area> merge(Area a, Area b) {
        List<Area> result = new LinkedList<>();

        if (a.contains(b)) {
            result.add(a.copy());
            return result;
        }
        if (b.contains(a)) {
            result.add(b.copy());
            return result;
        }

        Area overlapArea = a.overlap(b);
        if (overlapArea == null) {
            result.add(a.copy());
            result.add(b);
            return result;
        }
        
        result.add(a.copy());
        List<Area> subtracts = b.subtract(overlapArea);
        result.addAll(subtracts);

        return result;
    }
}
