pushd `dirname $0`/..
BASEDIR=`pwd`
popd

# Make sure the data directory is the root dir
DATADIR=$BASEDIR/data
rm -rf $DATADIR
mkdir $DATADIR
pushd $DATADIR

XTERM_COMMAND="xterm -e /bin/sh -c"
FELIX_DIR=/Users/davidb/apps/felix-framework-3.2.2
ZOOKEEPER_DIR=/Users/davidb/apps/zookeeper-3.3.3

echo Datadir: $DATADIR

# Launch Zoo Keeper
cp $ZOOKEEPER_DIR/conf/zoo_sample.cfg $ZOOKEEPER_DIR/conf/zoo.cfg
$XTERM_COMMAND "$ZOOKEEPER_DIR/bin/zkServer.sh start ; read dummy; $ZOOKEEPER_DIR/bin/zkServer.sh stop" &

# Sleep to give the zookeeper server a chance to start up. Shouldn't really be needed though...
# sleep 2

CONFIG_BASE=file://$BASEDIR/felix-config
echo Config Base: $CONFIG_BASE

# Create the CAS Zookeeper Configuration
mkdir $DATADIR/load
echo "zookeeper.host = 127.0.0.1" > $DATADIR/load/org.apache.cxf.dosgi.discovery.zookeeper.cfg

# Launch Frontend
$XTERM_COMMAND "java -Dfelix.config.properties=$CONFIG_BASE/config_frontend.properties -jar $FELIX_DIR/bin/felix.jar -b $FELIX_DIR/bundle $DATADIR/frontend" &

# Launch Database 1
$XTERM_COMMAND "java -Dfelix.config.properties=$CONFIG_BASE/config_database.properties -jar $FELIX_DIR/bin/felix.jar -b $FELIX_DIR/bundle $DATADIR/database1" &

# Launch Database 2
$XTERM_COMMAND "java -Dfelix.config.properties=$CONFIG_BASE/config_database.properties -jar $FELIX_DIR/bin/felix.jar -b $FELIX_DIR/bundle $DATADIR/database2" &

popd

