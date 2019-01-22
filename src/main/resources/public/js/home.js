import React from 'react';
import ReactDOM from 'react-dom';

class TemperatureDisplay extends React.Component {
    constructor(){
        super();
        this.state = {temperature: ''};
    }
    
    componentDidMount() {
    	fetch("/livingRoomTemperature/")
    	.then( function(response) {
    		  if (response.ok) {
    			  	return( response.text() );
    			  } else {
    			    var error = new Error(response.statusText)
    			    error.response = response
    			    throw error;
    			  }
    	} )
    	.then( text => this.setState((state) => ({ temperature: text })) );
    }
        
    render() {
        let temperature = this.state.temperature;
        return(
              <div className="temperature">{temperature}</div>
        );
    }
}

class ControlStateDisplay extends React.Component {
    constructor(){
        super();
        this.state = {
        		controlState: '',
        		color: 'white'};
        // This binding is necessary to make `this` work in the callback FFS!!
        this.toggleControl = this.toggleControl.bind(this);
    }
    
    componentDidMount() {
    	fetch("/heatingSystemControl/")
    	.then( function(response) {
    		  if (response.ok) {
    			  	return( response.text() );
    			  } else {
    			    var error = new Error(response.statusText)
    			    error.response = response
    			    throw error;
    			  }
    	} )
    	.then( text => this.setState((state) => ({ 
    		controlState: text == 'off' ? 'off' : 'auto',
    		color: text == 'off' ? 'lightgrey' : 'darkgrey', })) );
    }
    
    toggleControl(event) {
    	event.preventDefault();
    	var newState = this.state.controlState == 'off' ? 'auto' : 'off';
    	var newColour = newState == 'off' ? 'lightgrey' : 'darkgrey';

    	fetch("/setHeatingSystemControl/" + newState )
    	.then( function(response) {
    		if (response.ok) {
    			return( response.text() );
    		} else {
    			var error = new Error(response.statusText)
    			error.response = response
    			throw error;
    		}
    	} )
    	.then( text => this.setState((state) => ({ controlState: text, color: newColour })) )
    	.then( () => { this.props.reRender(); } );
    }
    
    render() {
        return(
              <div className="controlState" style={{background:this.state.color}} onClick={this.toggleControl}>
              		{this.state.controlState}
              </div>
        );
    }
}

class HeatingStateDisplay extends React.Component {
    constructor(){
        super();
        this.state = {heatingState: ''};
    }
    
    componentDidMount() {
    	fetch("/heatingSystemState/")
    	.then( function(response) {
    		  if (response.ok) {
    			  	return( response.text() );
    			  } else {
    			    var error = new Error(response.statusText)
    			    error.response = response
    			    throw error;
    			  }
    	} )
    	.then( text => this.setState((state) => ({ heatingState: text })) );
    }
        
    render() {
        let heatingState = this.state.heatingState;
        return(
              <div className="heatingState">{heatingState}</div>
        );
    }
}

class HomeDisplay extends React.Component {
	
	constructor(props) {
		super(props);
        this.state = {id: 1};
	}
	
	reRender() {	
	    this.setState( ( state ) => ({ id: this.state.id + 1}));
	}


	render() {
		return (
				<div className="homeDisplay" >
				<p><TemperatureDisplay /></p>
				<p> </p>
				<div className="rowState">
				<ControlStateDisplay reRender={this.reRender.bind(this)}/>
				<HeatingStateDisplay key="{id}"/>
				</div>
			</div>
		);
	}
}

// ========================================

ReactDOM.render(
  <HomeDisplay />,
  document.getElementById('homeDisplay')
);
