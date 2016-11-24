package org.carl.canal.queue;

public enum RabbitMQType {
	INSERT {
		@Override
		String exchange() {
			return "qgs";
		}

		@Override
		String routingKey() {
			return "q_task_writerows";
		}
	},UPDATE {
		@Override
		String exchange() {
			return "qgs";
		}

		@Override
		String routingKey() {
			return "q_task_updaterows";
		}
	},DELETE {
		@Override
		String exchange() {
			return "qgs";
		}

		@Override
		String routingKey() {
			return "q_task_deleterows";
		}
	};
	/**
	 * @show Rabbitmq exchange
	 * @return
	 */
	abstract String exchange();
	/**
	 * @show Rabbitmq routingKey
	 * @return
	 */
	abstract String routingKey();
}
