import SSocket from 'servisofts-socket';
import Parent from './index';
import Service from '../../index';

export default class Actions {
    static _getReducer = (props) => {
        return props.state[Parent.component + "Reducer"];
    }
    static getAll = (props) => {
        var reducer = Actions._getReducer(props);
        var data = reducer.data;
        if (!data) {
            if (reducer.estado == "cargando") return null;
            SSocket.send({
                //  service: Service.ServiceName,
                component: Parent.component,
                version: Parent.version,
                type: "getAll",
                estado: "cargando",
                key_usuario: "",
            })
            return null;
        }
        return data;
    }

    static getByKey = (key, props) => {
        var data = Actions.getAll(props);
        if (!data) return null;
        return data[key];
    }

    static registro = (data, key_servicio, props) => {
        SSocket.send({
            servicio: { key: key_servicio },
            component: Parent.component,
            version: Parent.version,
            type: "registro",
            estado: "cargando",
            key_usuario: "",
            data: data
        })
    }
    static getByNumber = (pay_order_number, key_servicio, props) => {
        SSocket.send({
            servicio: { key: key_servicio },
            component: Parent.component,
            version: Parent.version,
            type: "getByNumber",
            estado: "cargando",
            key_usuario: "",
            pay_order_number: pay_order_number
        })
    }
    static editar = (data, props) => {
        SSocket.send({
            // service: Service.ServiceName,
            component: Parent.component,
            version: Parent.version,
            type: "editar",
            estado: "cargando",
            key_usuario: "",
            data: data
        })
    }
    static eliminar = (data, props) => {
        SSocket.send({
            // service: Service.ServiceName,
            component: Parent.component,
            version: Parent.version,
            type: "editar",
            estado: "cargando",
            key_usuario: "",
            data: {
                ...data,
                estado: 0,
            }
        })
    }
}