import { Text, View } from 'react-native'
import { connect } from 'react-redux';
import { SButtom, SForm, SHr, SPage, SText, SNavigation, SLoad, SView, SIcon, STheme } from 'servisofts-component';

import React, { Component } from 'react'
import Parent from '../index'
class Solicitar extends Component {

  constructor(props) {
    super(props);
    this.state = {
    };
    this.key_servicio = SNavigation.getParam("key_servicio");
  }
  getContent() {
    return <SButtom type="secondary" style={{ fontSize: 15, color: STheme.color.text, backgroundColor: STheme.color.card, width: 300, borderRadius: 15 }}
      onPress={() => {
        Parent.Actions.registro({}, this.key_servicio, this.props);
      }}><SText style={{ padding: 8 }}> Solicitar orden </SText></SButtom>
  }
  getByNumber() {
    return <SButtom type="secondary" style={{ fontSize: 15, color: STheme.color.text, backgroundColor: STheme.color.card, width: 300, borderRadius: 15 }}
      onPress={() => {
        Parent.Actions.getByNumber(75819, this.key_servicio, this.props);
      }}><SText style={{ padding: 8 }}> getByNumber </SText></SButtom>
  }

  render() {
    return (
      <SPage title={'Lista de ' + Parent.component} disableScroll center>
        {this.getContent()}
        <SHr />
        {this.getByNumber()}
      </SPage>
    )
  }
}

const initStates = (state) => {
  return { state }
};
export default connect(initStates)(Solicitar);