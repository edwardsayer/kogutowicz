package net.anzix.kogutowicz.datasource;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



import java.util.Set;
import javax.validation.constraints.NotNull;
import net.anzix.kogutowicz.Mercator;
import net.anzix.kogutowicz.Projection;
import net.anzix.kogutowicz.TileDivision;
import net.anzix.kogutowicz.element.Element;
import org.kohsuke.MetaInfServices;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.core.xml.v0_6.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Inmemory datasource loaded from OSM xml file.
 * 
 * @author elek
 */
@MetaInfServices
public class OsmFile extends AbstractDatasource {

    private Logger logger = LoggerFactory.getLogger(OsmFile.class);

    private Projection projection = new Mercator();

    private Map<Element.Id, net.anzix.kogutowicz.element.Element> elements = new HashMap();

    private Double top;

    private Double left;

    private Double bottom;

    private Double right;

    private net.anzix.kogutowicz.element.Node br;

    @NotNull
    private File osmFile;

    private Set<Element.Id> returned = new HashSet();

    private boolean initialized = false;
    

    public OsmFile(File osmFile) {
        this.osmFile = osmFile;
    }

    public OsmFile() {
    }

    public void setOsmFile(File osmFile) {
        this.osmFile = osmFile;
    }

    @Override
    public void init(TileDivision division, Projection targetProjection) {
        this.projection = targetProjection;
        this.division = division;
        if (!initialized) {
            Date start = new Date();
            logger.debug("init osmFile dataSource " + osmFile);
            XmlReader reader = new XmlReader(osmFile, true, CompressionMethod.None);
            //FastXmlReader reader = new FastXmlReader(new File("/home/elek/Documents/nagykovacsi.osm"), true, CompressionMethod.None);
            reader.setSink(new Sink() {

                @Override
                public void process(EntityContainer arg0) {
                    Entity e = arg0.getEntity();
                    if (e instanceof Relation) {
//                    Relation r = (Relation) e;
//
//                    net.anzix.kogutowicz.element.Relation relation = new net.anzix.kogutowicz.element.Relation();
//                    copyTags(relation, r);
//                    for (RelationMember member : r.getMembers()) {
//                        if (member.getMemberType().equals(EntityType.Way)) {
//                            net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Way> rm =
//                                    new net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Way>(member.getMemberRole(), ways.get(member.getMemberId()));
//                            relation.addWay(rm);
//                        } else if (member.getMemberType().equals(EntityType.Node)) {
//                            net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Node> rm =
//                                    new net.anzix.kogutowicz.element.RelationMember<net.anzix.kogutowicz.element.Node>(member.getMemberRole(), nodes.get(member.getMemberId()));
//                            relation.addNode(rm);
//                        } else {
//                            throw new IllegalArgumentException();
//                        }
//                    }
//                    elements.put(new Id(net.anzix.kogutowicz.element.Relation.class, r.getId()), relation);
                    } else if (e instanceof Way) {
                        Way w = (Way) e;
                        net.anzix.kogutowicz.element.Way way;
                        if (w.isClosed()) {
                            way = new net.anzix.kogutowicz.element.Area();
                        } else {
                            way = new net.anzix.kogutowicz.element.Way();
                        }
                        way.setId(new Element.Id(net.anzix.kogutowicz.element.Way.class, w.getId()));
                        copyTags(way, e);

                        for (WayNode wn : w.getWayNodes()) {
                            net.anzix.kogutowicz.element.Node n = (net.anzix.kogutowicz.element.Node) elements.get(new Element.Id(net.anzix.kogutowicz.element.Node.class, wn.getNodeId()));
                            if (n == null) {
                                logger.warn("The related node does'n exists way={} node={}", w.getId(), wn.getNodeId());
                                return;
                            }
                            way.addNode(n);
                        }
                        elements.put(way.getId(), way);
                        indexWay(way);
                    } else if (e instanceof Node) {
                        Node n = (Node) e;
                        net.anzix.kogutowicz.element.Node node = net.anzix.kogutowicz.element.Node.valueOf(projection, n.getLongitude(), n.getLatitude());
                        node.setId(new Element.Id(net.anzix.kogutowicz.element.Node.class, n.getId()));
                        copyTags(node, e);
                        elements.put(node.getId(), node);
                        if (top == null) {
                            top = node.getLatitude();
                            bottom = node.getLatitude();
                            right = node.getLongitude();
                            left = node.getLongitude();
                        }
                        if (node.getLongitude() < left) {
                            left = node.getLongitude();
                        }
                        if (node.getLongitude() > right) {
                            right = node.getLongitude();
                        }
                        if (node.getLatitude() > top) {
                            top = node.getLatitude();
                        }
                        if (node.getLatitude() < bottom) {
                            bottom = node.getLatitude();
                        }
                        indexNode(node);

                    }

                }

                @Override
                public void complete() {
                    logger.debug("File " + osmFile.getAbsolutePath() + " is loaded.");
                    logger.debug(left + " " + top + " " + right + " " + bottom);
                    initialized = true;
                }

                @Override
                public void release() {
                }
            });
            reader.run();
            logger.debug("file loaded in " + (new Date().getTime() - start.getTime()) / 1000 + " sec");
        }
    }

 
    public void copyTags(net.anzix.kogutowicz.element.Element ownElement, Entity osmElement) {
        for (Tag tag : osmElement.getTags()) {
            ownElement.addTag(tag.getKey(), tag.getValue());
        }

    }

  

    @Override
    public void reset() {
        returned.clear();
    }
}
