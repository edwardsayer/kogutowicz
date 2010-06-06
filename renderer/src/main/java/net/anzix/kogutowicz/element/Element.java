package net.anzix.kogutowicz.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract map element from any source.
 * 
 * @author elek
 */
public abstract class Element {

    private Element.Id id;

    protected List<Tag> tags = new ArrayList();

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addTag(String key, String value) {
        tags.add(new Tag(key, value));
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean containsTag(String name) {
        for (Tag tag : tags) {
            if (tag.getKey().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTag(String name, String value) {
        for (Tag tag : tags) {
            if (tag.getKey().equals(name) && tag.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }



    public boolean tagValueIn(String name, String[] string) {
        String value = tagValue(name);
        if (value != null && Arrays.asList(string).contains(value)) {
            return true;
        }
        return false;
    }

    public String tagValue(String tagName) {
        for (Tag tag : tags) {
            if (tag.getKey().equals(tagName)) {
                return tag.getValue();
            }
        }
        return null;
    }

    /**
     * Return value of specific tag.
     *
     * If more than one tag exsists the first will be choosen.
     *
     * @param key
     * @return
     */
    public String getTagValue(String key) {
        for (Tag tag : tags) {
            if (tag.getKey().equals(key)) {
                return tag.getValue();
            }
        }
        return "";
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public abstract boolean isIntersect(Box box);

    public static class Id {

        private Class<? extends Element> type;

        private Long id;

        public Id(Class<? extends Element> type, Long id) {
            this.type = type;
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Class<? extends Element> getType() {
            return type;
        }

        public void setType(Class<? extends Element> type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Id other = (Id) obj;
            if (this.type != other.type && (this.type == null || !this.type.equals(other.type))) {
                return false;
            }
            if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
            return hash;
        }
    }
}
