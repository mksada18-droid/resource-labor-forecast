# 🔧 Install Requirements - Simple Steps

Follow these commands exactly to install everything needed.

## Step 1: Install Homebrew (2 minutes)

Homebrew is a package manager for Mac. Copy and paste this command:

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

**What happens:**
- You'll be asked for your Mac password
- Press Enter to continue
- Wait 1-2 minutes for installation

**After installation completes**, run these commands (Homebrew will tell you to):

```bash
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"
```

**Verify Homebrew is installed:**
```bash
brew --version
```

You should see: `Homebrew 4.x.x`

---

## Step 2: Install Java 17 (3 minutes)

```bash
# Install Java 17
brew install openjdk@17

# Link Java 17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk

# Add to PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc

# Reload shell
source ~/.zshrc
```

**Verify Java 17 is installed:**
```bash
java -version
```

You should see: `openjdk version "17.x.x"`

---

## Step 3: Install Maven (1 minute)

```bash
# Install Maven
brew install maven

# Verify installation
mvn -version
```

You should see: `Apache Maven 3.x.x` and `Java version: 17.x.x`

---

## Step 4: Install Node.js (2 minutes)

```bash
# Install Node.js
brew install node

# Verify installation
node -version
npm -version
```

You should see version numbers for both.

---

## ✅ Verification Checklist

Run these commands to verify everything is installed:

```bash
brew --version    # Should show Homebrew version
java -version     # Should show Java 17
mvn -version      # Should show Maven 3.x
node -version     # Should show Node 18+
npm -version      # Should show npm 9+
```

---

## 🚀 Next Step: Run Your Application

Once all installations are complete, run:

```bash
# Terminal 1 - Start Backend
cd /Users/sudarshan/Desktop/backend
./run.sh
```

Wait for "Application Started" message, then open a NEW terminal:

```bash
# Terminal 2 - Start Frontend
cd /Users/sudarshan/Desktop/frontend
npm install
npm start
```

Your browser will open to: **http://localhost:3000**

---

## 🐛 Troubleshooting

### Problem: "command not found" after installation

**Solution:** Reload your shell
```bash
source ~/.zshrc
```

### Problem: Java 8 still showing instead of Java 17

**Solution:** Set Java 17 as default
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
echo 'export JAVA_HOME=/opt/homebrew/opt/openjdk@17' >> ~/.zshrc
source ~/.zshrc
java -version
```

### Problem: Permission denied

**Solution:** Use sudo
```bash
sudo [command]
```

---

## 📝 Quick Copy-Paste All Commands

If you want to run everything at once:

```bash
# Install Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Configure Homebrew
echo 'eval "$(/opt/homebrew/bin/brew shellenv)"' >> ~/.zprofile
eval "$(/opt/homebrew/bin/brew shellenv)"

# Install Java 17
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc

# Install Maven
brew install maven

# Install Node.js
brew install node

# Reload shell
source ~/.zshrc

# Verify installations
echo "=== Checking Installations ==="
brew --version
java -version
mvn -version
node -version
npm -version
```

---

## ⏱️ Total Time: ~10 minutes

- Homebrew: 2 min
- Java 17: 3 min
- Maven: 1 min
- Node.js: 2 min
- Verification: 1 min

---

**After installation, you're ready to run your application!** 🎉