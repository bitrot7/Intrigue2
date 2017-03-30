package com.mk.intrigue;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


/*
BodyPart: Root
BodyPart: pelvis
BodyPart: spine_01
BodyPart: spine_02
BodyPart: spine_03
BodyPart: clavicle_l
BodyPart: upperarm_l
BodyPart: lowerarm_l
BodyPart: hand_l
BodyPart: HockeyStick
BodyPart: index_01_l
BodyPart: index_02_l
BodyPart: index_03_l
BodyPart: index_03_l_end
BodyPart: middle_01_l
BodyPart: middle_02_l
BodyPart: middle_03_l
BodyPart: middle_03_l_end
BodyPart: pinky_01_l
BodyPart: pinky_02_l
BodyPart: pinky_03_l
BodyPart: pinky_03_l_end
BodyPart: ring_01_l
BodyPart: ring_02_l
BodyPart: ring_03_l
BodyPart: ring_03_l_end
BodyPart: thumb_01_l
BodyPart: thumb_02_l
BodyPart: thumb_03_l
BodyPart: thumb_03_l_end
BodyPart: clavicle_r
BodyPart: upperarm_r
BodyPart: lowerarm_r
BodyPart: hand_r
BodyPart: index_01_r
BodyPart: index_02_r
BodyPart: index_03_r
BodyPart: index_03_r_end
BodyPart: middle_01_r
BodyPart: middle_02_r
BodyPart: middle_03_r
BodyPart: middle_03_r_end
BodyPart: pinky_01_r
BodyPart: pinky_02_r
BodyPart: pinky_03_r
BodyPart: pinky_03_r_end
BodyPart: ring_01_r
BodyPart: ring_02_r
BodyPart: ring_03_r
BodyPart: ring_03_r_end
BodyPart: thumb_01_r
BodyPart: thumb_02_r
BodyPart: thumb_03_r
BodyPart: thumb_03_r_end
BodyPart: neck_01
BodyPart: head
BodyPart: head_end
BodyPart: thigh_l
BodyPart: calf_l
BodyPart: foot_l
BodyPart: HockeySkateLeft
BodyPart: ball_l
BodyPart: ball_l_end
BodyPart: thigh_r
BodyPart: calf_r
BodyPart: foot_r
BodyPart: HockeySkateRight
BodyPart: ball_r
BodyPart: ball_r_end
*/
public class DefaultKinematicRagdoll {
	private Array<String> bone_names = new Array<String>();
	//private Array<Matrix4> transforms = new Array<Matrix4>();
	//private Array<btCollisionShape> cshapes = new Array<btCollisionShape>();
	//private Array<ModelInstance> models = new Array<ModelInstance>();
	//private Array<Node> bones = new Array<Node>();
	private Array<AtomicPhysicalObject> pieces = new Array<AtomicPhysicalObject>();
	//private Array<Float> masses = new Array<Float>();
	//private Array<Vector3> inertias = new Array<Vector3>();
	private final Matrix4 fiddle = new Matrix4();
	private final Matrix4 fiddle2 = new Matrix4();
	//private Model asset;
	private ModelInstance model;
	private Vector3 position = new Vector3();
	public DefaultKinematicRagdoll(Model m) {
		fiddle.trn(2000, 500, -2000);
		fiddle2.trn(10, 50, -700);
		//asset= m;
		//for( NodePart np : asset.getNode("Root", true).parts) {
	//	System.out.println(np.bones);
		//}
		model = new ModelInstance(m);
		model.transform.set(fiddle);
		fiddle.getTranslation(position);
		//iterateNodes(model, asset.getNode("Root", true));
	}
	public AtomicPhysicalObject getBodyPart(String mhname) {
		int i = 0;//
		for(String n : bone_names) {
			if(n.equalsIgnoreCase(mhname)) {
				return pieces.get(i);
			}
		}
		i++;
		return null;
	}
	public void update(){
		int i = 0;
		for(AtomicPhysicalObject a : pieces) {
			Matrix4 t= new Matrix4();
			//Vector3 p = new Vector3();
			a.getMotionState().getWorldTransform(t);
			Node n = model.getNode(bone_names.get(i));
			//t.getTranslation(a.getRigidBody().getCenterOfMassPosition());
			//p.scl(.01f);
			//n.translation.set(a.getRigidBody().getCenterOfMassPosition()/*.scl(.0005f,.001f, .0005f)*/);
			
			//t.getRotation(n.rotation);
			if(n.id.equals("Root")) {
				model.transform.set(t);
			}
			//n.calculateWorldTransform();
			model.calculateTransforms();
			i++;
		}
		//model.calculateTransforms();
	}
	public Array<AtomicPhysicalObject> getAllBodyParts() {
		return pieces;
	}
	public ModelInstance getAllInstances() {
		return model;
	}
	/*private void iterateNodes(ModelInstance m, Node n) {
		System.out.println("Creating Atomic Piece for Node: " + n.id);
		//for(NodePart np : n.parts) {
			System.out.println("\t NodeParts: " + n.parts.size);
		//}
		//ModelInstance cur_model =  new ModelInstance(m, fiddle,n.id ,true, true, false);
		btCollisionShape cur_shape = new btCapsuleShape(10f, 10f);
		float cur_mass = 3f;
		Node cur_node = n; 
		
		Node node = m.getNode(n.id);
		//node.inheritTransform = false;
		//cur_model.transform.set(node.globalTransform);
		//node.translation.set(0,0,0);
		//node.scale.set(100000,1000000,1000000);
		///node.rotation.idt();
		//cur_model.calculateTransforms();
		bones.add(cur_node);
		cshapes.add(cur_shape);
		masses.add(cur_mass);
		inertias.add(new Vector3());
		bone_names.add(n.id);
		//models.add(cur_model);
		
		Vector3 cur_inertia = new Vector3();
		if(cur_mass != 0) {
			cur_shape.calculateLocalInertia(cur_mass, cur_inertia);
		}
		fiddle.translate(0,0,1);
		pieces.add(new AtomicPhysicalObject(cur_shape, cur_mass, cur_inertia, fiddle));
		pieces.get(pieces.size-1).getRigidBody().setActivationState(Collision.DISABLE_DEACTIVATION);
		if(n.hasChildren()) {
			for(Node n2 : n.getChildren()) {
				iterateNodes(m,n2);
			}
		}
	}*/
}