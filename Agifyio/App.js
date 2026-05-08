import React, { useState, useRef } from "react";
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  ActivityIndicator,
  Animated,
  Dimensions,
  ImageBackground,
  ScrollView,
  Image,
  StatusBar,
} from "react-native";

const { width, height } = Dimensions.get("window");

export default function App() {
  const [name, setName] = useState("");
  const [count, setCount] = useState(null);
  const [loading, setLoading] = useState(false);
  const [age, setAge] = useState(null);

  const Fade = useRef(new Animated.Value(0)).current;
  const Scale = useRef(new Animated.Value(0.85)).current;
  const ButtonScale = useRef(new Animated.Value(1)).current;

  const fetchAge = async () => {
    if (!name.trim()) {
      alert("Please enter your name");
      return;
    }

    try {
      setLoading(true);

      Fade.setValue(0);
      Scale.setValue(0.85);

      const response = await fetch(
        `https://api.agify.io/?name=${encodeURIComponent(name.trim())}`
      );
      const data = await response.json();

      setAge(data.age);
      setCount(data.count);

      Animated.parallel([
        Animated.timing(Fade, {
          toValue: 1,
          duration: 600,
          useNativeDriver: true,
        }),
        Animated.spring(Scale, {
          toValue: 1,
          friction: 7,
          tension: 40,
          useNativeDriver: true,
        }),
      ]).start();
    } catch (e) {
      alert("Something went wrong. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handlePressIn = () => {
    Animated.spring(ButtonScale, {
      toValue: 0.95,
      useNativeDriver: true,
    }).start();
  };

  const handlePressOut = () => {
    Animated.spring(ButtonScale, {
      toValue: 1,
      useNativeDriver: true,
    }).start();
  };

  return (
    <ImageBackground
      source={require("./assets/Sakura.jpg")}
      resizeMode="cover"
      style={styles.background}
    >
      <StatusBar barStyle="dark-content" translucent backgroundColor="transparent" />

      <View style={styles.overlay}>
        <Image
          source={require("./assets/sample.gif")}
          style={[styles.gifLayer, { top: "5%", left: "10%", opacity: 0.16 }]}
          resizeMode="cover"
          pointerEvents="none"
        />
        <Image
          source={require("./assets/sample.gif")}
          style={[styles.gifLayer, { bottom: "15%", right: "5%", opacity: 0.13, transform: [{ scale: 0.85 }] }]}
          resizeMode="cover"
          pointerEvents="none"
        />

        <ScrollView 
          contentContainerStyle={styles.scrollContainer}
          showsVerticalScrollIndicator={false}
          keyboardShouldPersistTaps="handled"
        >
          <SafeAreaView style={styles.container}>
            <View style={styles.topBloom} />

            <View style={styles.header}>
              <Text style={styles.heading}>🌸 Sakura Memory</Text>
              <Text style={styles.subtitle}>
                A gentle place where names bloom into stories
              </Text>
            </View>

            <View style={styles.card}>
              <View style={styles.inputWrapper}>
                <Text style={styles.inputLabel}>YOUR NAME</Text>
                <TextInput
                  style={styles.input}
                  placeholder="Enter your name..."
                  placeholderTextColor="#C8A0B0"
                  value={name}
                  onChangeText={setName}
                  autoCapitalize="words"
                  returnKeyType="done"
                />
              </View>

              <TouchableOpacity 
                style={styles.button} 
                onPress={fetchAge}
                activeOpacity={0.9}
                onPressIn={handlePressIn}
                onPressOut={handlePressOut}
              >
                <Animated.View style={{ transform: [{ scale: ButtonScale }] }}>
                  <Text style={styles.buttonText}>Continue Journey 🌸</Text>
                </Animated.View>
              </TouchableOpacity>
            </View>

            <Animated.View
              style={[
                styles.resultBox,
                { opacity: Fade, transform: [{ scale: Scale }] },
              ]}
            >
              {loading ? (
                <View style={styles.loadingContainer}>
                  <ActivityIndicator color="#D48A9E" size="large" />
                  <Text style={styles.loadingText}>Whispering to the blossoms...</Text>
                </View>
              ) : age !== null ? (
                <View style={styles.resultContent}>
                  <View style={styles.resultHeader}>
                    <Text style={styles.resultTitle}>Your Memory</Text>
                    <View style={styles.divider} />
                  </View>

                  <View style={styles.resultRow}>
                    <Text style={styles.resultLabel}>Name</Text>
                    <Text style={styles.resultValue}>{name}</Text>
                  </View>

                  <View style={styles.resultRow}>
                    <Text style={styles.resultLabel}>Estimated Age</Text>
                    <Text style={styles.ageValue}>{age}</Text>
                  </View>

                  <View style={styles.resultRow}>
                    <Text style={styles.resultLabel}>Stories Found</Text>
                    <Text style={styles.resultValue}>{count?.toLocaleString() || "—"}</Text>
                  </View>

                  <Text style={styles.poeticLine}>
                    "In the quiet wind of time, your name carries {age} springs..."
                  </Text>
                </View>
              ) : (
                <View style={styles.emptyState}>
                  <Text style={styles.emptyEmoji}>🌸</Text>
                  <Text style={styles.emptyText}>Enter a name to awaken a memory</Text>
                </View>
              )}
            </Animated.View>

            <View style={styles.bottomDecoration}>
              <Text style={styles.footerText}>✧ Cherish every moment ✧</Text>
            </View>
          </SafeAreaView>
        </ScrollView>
      </View>
    </ImageBackground>
  );
}

const styles = StyleSheet.create({
  background: { flex: 1 },

  overlay: {
    flex: 1,
    backgroundColor: "rgba(255, 242, 248, 0.92)",
  },

  gifLayer: {
    position: "absolute",
    width: width * 0.7,
    height: height * 0.5,
    zIndex: 1,
  },

  scrollContainer: {
    flexGrow: 1,
    justifyContent: "center",
    paddingBottom: 40,
  },

  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 24,
  },

  topBloom: {
    position: "absolute",
    top: -100,
    width: width * 1.5,
    height: width * 1.5,
    backgroundColor: "#F8D7E3",
    opacity: 0.08,
    borderRadius: 999,
    zIndex: -1,
  },

  header: {
    alignItems: "center",
    marginBottom: 40,
    zIndex: 2,
  },

  heading: {
    fontSize: 42,
    fontWeight: "700",
    color: "#6D3F4F",
    textAlign: "center",
    marginBottom: 8,
    textShadowColor: "rgba(255, 182, 193, 0.4)",
    textShadowOffset: { width: 0, height: 2 },
    textShadowRadius: 6,
  },

  subtitle: {
    fontSize: 15.5,
    color: "#8A5A6F",
    textAlign: "center",
    lineHeight: 22,
    paddingHorizontal: 20,
  },

  card: {
    width: "80%",
    backgroundColor: "rgba(255, 255, 255, 0.96)",
    borderRadius: 28,
    padding: 28,
    paddingBottom: 32,
    shadowColor: "#E8B0C4",
    shadowOffset: { width: 0, height: 12 },
    shadowOpacity: 0.22,
    shadowRadius: 24,
    elevation: 10,
    borderWidth: 1.5,
    borderColor: "#F5D0E0",
    zIndex: 3,
  },

  inputWrapper: {
    marginBottom: 24,
  },

  inputLabel: {
    fontSize: 13,
    fontWeight: "600",
    color: "#A46A7F",
    marginBottom: 8,
    letterSpacing: 1.2,
    paddingLeft: 4,
  },

  input: {
    width: "100%",
    height: 58,
    backgroundColor: "#FFF9FB",
    borderRadius: 18,
    borderWidth: 1.5,
    borderColor: "#E8B8CC",
    paddingHorizontal: 20,
    fontSize: 17,
    color: "#5C2F42",
  },

  button: {
    backgroundColor: "#E8A9C0",
    paddingVertical: 16,
    borderRadius: 26,
    alignItems: "center",
    shadowColor: "#D88AA8",
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.3,
    shadowRadius: 12,
    elevation: 8,
  },

  buttonText: {
    color: "#5C2F42",
    fontSize: 17,
    fontWeight: "700",
    letterSpacing: 0.6,
  },
resultBox: {
  marginTop: 32,
  width: "80%",
  alignSelf: "center",   
  backgroundColor: "rgba(255, 255, 255, 0.95)",
  borderRadius: 26,
  padding: 20,          
  borderWidth: 1.5,
  borderColor: "#F5D0E0",
  shadowColor: "#E8B0C4",
  shadowOffset: { width: 0, height: 10 },
  shadowOpacity: 0.18,
  shadowRadius: 20,
  elevation: 8,
  zIndex: 2,
    alignItems: "center",
},

  loadingContainer: {
    alignItems: "center",
    paddingVertical: 40,
  },
  loadingText: {
    marginTop: 16,
    color: "#A46A7F",
    fontSize: 15,
  },
  resultContent: { width: "60%" },
  resultHeader: { alignItems: "center", marginBottom: 20 },
  resultTitle: { fontSize: 18, fontWeight: "700", color: "#6D3F4F" },
  divider: { width: 60, height: 2, backgroundColor: "#E8B8CC", marginTop: 8, borderRadius: 2 },
  resultRow: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingVertical: 11,
    borderBottomWidth: 1,
    borderBottomColor: "#F9E4ED",
  },
  resultLabel: { fontSize: 15.5, color: "#8A5A6F" },
  resultValue: { fontSize: 16.5, fontWeight: "600", color: "#5C2F42" },
  ageValue: { fontSize: 42, fontWeight: "700", color: "#C15A7D", lineHeight: 48 },
  poeticLine: {
    marginTop: 22,
    textAlign: "center",
    fontSize: 14.5,
    fontStyle: "italic",
    color: "#9B6F7D",
    lineHeight: 22,
  },
  emptyState: { alignItems: "center", paddingVertical: 50 },
  emptyEmoji: { fontSize: 48, marginBottom: 12 },
  emptyText: { color: "#A46A7F", fontSize: 15.5, textAlign: "center" },
  bottomDecoration: { marginTop: 50, alignItems: "center" },
  footerText: { color: "#B88A9E", fontSize: 13, letterSpacing: 2, opacity: 0.75 },
});