# simulator_dashboard.py
"""Script That Subscribes to the Thermostat Messages 
and Visualizes them Using Plotly's Dash"""
import dash
from dash import dcc, html, Output, Input
import plotly.graph_objs as go
from pubnub.enums import PNStatusCategory
from pubnub.pubnub import PubNub
from pubnub.pnconfiguration import PNConfiguration
from pubnub.callbacks import SubscribeCallback
import keys 

# create Dash app named with the global variable __name__ 
app = dash.Dash(__name__)

# create app's layout
app.layout = html.Div([
    dcc.Graph(id='temperature-gauge', style={'marginBottom': '0px'}),  
    html.Div(id='fahrenheit-text', 
             style={'textAlign': 'center', 
                    'fontSize': 30, 'fontFamily': 'Arial'}),
    html.Div([
        html.Div([
            html.Div(id='low-warning-light', 
                     style={'display': 'inline-block', 'width': '20px', 
                            'height': '20px', 'borderRadius': '50%'}),
            html.Div("Too Low", 
                     style={'display': 'inline-block', 
                            'fontSize': 16, 'fontFamily': 'Arial'})
        ], style={'textAlign': 'center', 'marginTop': '10px'}),
        html.Div([
            html.Div(id='high-warning-light', 
                     style={'display': 'inline-block', 'width': '20px', 
                            'height': '20px', 'borderRadius': '50%'}),
            html.Div("Too High", 
                     style={'display': 'inline-block', 
                            'fontSize': 16, 'fontFamily': 'Arial'})
        ], style={'textAlign': 'center', 
                  'marginTop': '10px'})
    ]),
    dcc.Interval(id='interval-component', 
                 interval=1000)  # refresh once per second
], style={'fontFamily': 'Arial', 'width': '500px', 'margin': '0 auto'})

# global variable to store latest message received from thermostat
latest_message = None

class ThermostatSubscribeCallback(SubscribeCallback):
    """ThermostatSubscribeCallback receives messages from PubNub."""
    
    def __init__(self, max_messages=1000):
        """Creates instance variables for tracking number of messages."""
        self.message_count = 0
        self.MAX_MESSAGES = max_messages  # 1000 by default
        super().__init__()  # call superclass's init

    def status(self, pubnub, status):
        """Processes status notifications from PubNub."""
        if status.category == PNStatusCategory.PNConnectedCategory:
            print('Subscribed')
        elif status.category == PNStatusCategory.PNAcknowledgmentCategory:
            print('Unsubscribed')
        else:
            print(status.category)
 
    def message(self, pubnub, message):
        """Receives each message PubNub pushes."""
        global latest_message
        self.message_count += 1
        latest_message = message.message
         
        # if MAX_MESSAGES reached, unsubscribe from PubNub channel
        if self.message_count == self.MAX_MESSAGES:
            pubnub.unsubscribe_all()
         
# set up the Dash app's callback that updates the UI 
@app.callback(
    [Output('temperature-gauge', 'figure'),
     Output('fahrenheit-text', 'children'),
     Output('low-warning-light', 'style'),
     Output('high-warning-light', 'style')],
    [Input('interval-component', 'n_intervals')]
)
def update_dashboard(n):
    # do not update unless latest_message contains a message
    if latest_message is None:
        raise dash.exceptions.PreventUpdate

    # get the temperature from the message and convert it to Fahrenheit
    temperature_c = latest_message.get('Temperature', 0)
    temperature_f = temperature_c * 9 / 5 + 32
    fahrenheit_text = f"{temperature_f:.1f}°F"

    # create the gauge graph object (go) for the next update
    gauge = go.Figure(go.Indicator(
        mode="gauge+number",
        value=temperature_c,
        domain={'x': [0, 1], 'y': [0, 1]},
        title={'text': "Temperature (°C)"},
        number={'font': {'family': "Arial"}}, 
        gauge={
            'axis': {'range': [-25, 45], 
                     'tickfont': {'size': 12, 'family': 'Arial'}},
            'bar': {'color': "darkblue"},
            'steps': [
                {'range': [-25, 0], 'color': 'cyan'},
                {'range': [0, 45], 'color': 'orange'}
            ],
            'threshold': {
                'line': {'color': "red", 'width': 4},
                'thickness': 0.75,
                'value': temperature_c
            }
        }
    ))

    # update warning lights based on values in latest message
    low_light = latest_message.get('Too_Low', False)
    high_light = latest_message.get('Too_High', False)

    low_light_style = {
        'display': 'inline-block',
        'width': '20px', 
        'height': '20px', 
        'borderRadius': '50%', 
        'backgroundColor': 'red' if low_light else 'grey',
        'marginRight': '10px'
    }
    high_light_style = {
        'display': 'inline-block',
        'width': '20px', 
        'height': '20px', 
        'borderRadius': '50%', 
        'backgroundColor': 'red' if high_light else 'grey',
        'marginRight': '10px'
    }

    return gauge, fahrenheit_text, low_light_style, high_light_style

# launch Dash server when this file is run as a script
if __name__ == '__main__':
    # PubNub client subscription info
    config = PNConfiguration()
    config.subscribe_key = keys.pubnub_subscribe_key
    config.user_id = keys.pubnub_user_id # your login ID
    
    # create the pubnub client
    pubnub = PubNub(config)
    
    # channel we'll subscribe to
    CHANNEL_NAME = 'deitel-thermostat-simulator'
    
    # set up the listener and subscribe to the channel
    pubnub.add_listener(ThermostatSubscribeCallback())
    pubnub.subscribe().channels(CHANNEL_NAME).execute()

    app.run_server(debug=True)
    
