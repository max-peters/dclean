package database.main;

public abstract class Instance {
	public String			identity;
	protected InstanceList	list;

	public Instance(String identity, InstanceList list) {
		this.identity = identity;
		this.list = list;
	}

	public abstract String toString();
}