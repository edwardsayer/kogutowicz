package net.anzix.kogutowicz.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.anzix.kogutowicz.TileCoord;
import net.anzix.kogutowicz.geometry.GeometryElementOnLayer;
import net.anzix.kogutowicz.geometry.Icon;

/**
 * Hide icons if there isn't enough space between them.
 *
 * @author elek
 */
public class CollisionDetector implements GeometryCacheProcessor {

    @Override
    public void process(GeometryCache cache, RenderContext context) {
        TileCoord from = context.getTileStart();
        TileCoord to = context.getTileEnd();
        for (int x = from.getX(); x <= to.getX(); x++) {
            for (int y = from.getY(); y <= to.getY(); y++) {
                Set<Icon> is = new HashSet();

                filterIcons(cache, new TileCoord(x, y), is);
                filterIcons(cache, new TileCoord(x + 1, y), is);
                filterIcons(cache, new TileCoord(x, y + 1), is);

                if (is.size() < 2) {
                    continue;
                }

                List<Icon> icons = new ArrayList(is);
                for (int i = 0; i < icons.size() - 1; i++) {
                    for (int j = i + 1; j < icons.size(); j++) {
                        Icon i1 = (Icon) icons.get(i);
                        Icon i2 = (Icon) icons.get(j);
                        if (i1.isHidden() || i2.isHidden()) {
                            continue;
                        }
                        double d = Math.sqrt(Math.pow(i1.getX() - i2.getX(), 2) + Math.pow(i1.getY() - i2.getY(), 2));
                        if (d < 20) {
                            i2.setHidden(true);

                        }
                    }
                }


            }
        }

    }

    private void filterIcons(GeometryCache cache, TileCoord tileCoord, Collection<Icon> icons) {
        for (GeometryElementOnLayer gol : cache.getElements(tileCoord)) {
            if (!gol.getElement().isHidden() && gol.getElement() instanceof Icon) {
                icons.add((Icon) gol.getElement());
            }
        }
    }
}
