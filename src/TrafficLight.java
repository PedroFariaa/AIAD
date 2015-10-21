import jade.core.*;
import jade.core.behaviours.*;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

// classe do agente
public class TrafficLight extends Agent {

	private String color;
	private int timer;
	DFAgentDescription[]  result;
	// classe do behaviour
	class TrafficLightBehaviour extends SimpleBehaviour {
		private int n = 0;
		private Agent ag;
		// construtor do behaviour
		public TrafficLightBehaviour(Agent a) {
			super(a);
			ag = a;
		}

		// método action
		public void action() {
			if ( color.equals("vermelho"))
			{
				// envia mensagem "pong" inicial a todos os agentes "ping"
				ACLMessage send2 = new ACLMessage(ACLMessage.INFORM);
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd1 = new ServiceDescription();
				sd1.setType("verde");
				template.addServices(sd1);
				try {
					result = DFService.search(ag, template);
				} catch (FIPAException e) {
					e.printStackTrace();
				}
				for(int i=0; i<result.length; ++i){
					send2.addReceiver(result[i].getName());
					//System.out.println(result[i].getName());
				}
				send2.setContent("Hey tu, muda para vermelho");
				send(send2);

				ACLMessage reply = blockingReceive();
				if(reply.getPerformative() == ACLMessage.INFORM) {
					System.out.println(++n + " " + getLocalName() + ": recebi " + reply.getContent());
					if ( reply.getContent().equals("oui oui, my dear friend"))
					{
						color = "verde";
						System.out.println("Agora sou verde ahahah!");
					}
				}

				timer += 1;

			}
			else
			{
				ACLMessage request = blockingReceive();
				System.out.println("RECEIVE");
				if(request.getPerformative() == ACLMessage.INFORM) {
					System.out.println(++n + " " + getLocalName() + ": recebi " + request.getContent());
					if ( request.getContent().equals("Hey tu, muda para vermelho"))
					{
						ACLMessage reply = request.createReply();
						reply.setContent("oui oui, my dear friend");
						send(reply);
						color = "vermelho";
						System.out.println("Agora sou vermelho, oh!");
					}
				}

			}

			n += 1;

		}

		@Override
		public boolean done() {
			return n==100;
		}
	}// fim da classe PingPongBehaviour


	// método setup
	protected void setup() {
		String tipo = "";
		// obtém argumentos
		Object[] args = getArguments();
		if(args != null && args.length > 0) {
			tipo = (String) args[0];
			color = tipo;
			timer = 0;
		} else {
			System.out.println("Não especificou o tipo");
		}

		// regista agente no DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType(tipo);

		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}

		// cria behaviour
		TrafficLightBehaviour b = new TrafficLightBehaviour(this);
		addBehaviour(b);




	}   // fim do metodo setup

	// método takeDown
	protected void takeDown() {
		// retira registo no DF
		try {
			DFService.deregister(this);  
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

}   // fim da classe PingPong

