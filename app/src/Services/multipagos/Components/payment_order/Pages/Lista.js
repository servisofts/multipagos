import React, { Component } from 'react';
import { connect } from 'react-redux';
import { SDate, SIcon, SLoad, SNavigation, SPage, SPopup, STable2, SView } from 'servisofts-component';
import Parent from '../index';



class Lista extends Component {

  constructor(props) {
    super(props);
    this.state = {
    };
    this.key_servicio = SNavigation.getParam("key_servicio");
  }

  getContent() {
    var data = Parent.Actions.getAll(this.props);
    if (!data) return <SLoad />;
    return <STable2
      header={[
        { key: "index", label: "#", width: 50 },
        { key: "key_config", label: "key_config", width: 150 },
        { key: "key_servicio", label: "key_servicio", width: 150 },
        { key: "fecha_on", label: "fecha_on", width: 150, render: (item) => { return new SDate(item).toString("yyyy-MM-dd hh:mm:ss") } },
        { key: "pay_order_number", label: "pay_order_number", width: 150 },
        { key: "pay_official_number", label: "pay_official_number", width: 150 },
        { key: "-getByNumer", label: "Consultar", width: 50, center: true, component: (item) => { return <SView onPress={() => { Parent.Actions.getByNumber(item.pay_order_number, item.key_servicio, this.props) }}> <SIcon name={"Ajustes"} width={35} /></SView> } },
        // { key: "data", label: "Data", width: 50, center: true, component: (item) => { return <SView onPress={() => { 
        //   SPopup.alert(JSON.stringify(item,"\n","\t"))
        // }}> <SIcon name={"Alert"} width={35} /></SView> } },
      ]}
      data={data}
      filter={(item) => {
        if (!this.key_servicio) return true;
        return item.key_servicio == this.key_servicio
      }}
    />
  }


  render() {
    return (
      <SPage title={'Lista de ' + Parent.component} disableScroll center>
        {this.getContent()}
      </SPage>
    )
  }
}

const initStates = (state) => {
  return { state }
};
export default connect(initStates)(Lista);