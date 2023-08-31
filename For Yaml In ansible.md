For Yaml In ansible
# after Launch the instance 
sudo apt-get update
sudo adduser <user name> then enter password    
sudo su <user name>
sai@<ip>:ll
Then install to <Ansible> by using this command sudo aapt-get install ansible
ansible --version: to check the version
Then Create a <Directory> mkdir ansible
Then we create a our own <ansible.cfg> file
In Above Ansible.cfg File ### below points must to mentioned 
vi ansible.cfg  ---> [headers] ---> host_key_check = false
After we must create a our own Inventory File <vi inventory>.
In inventory file [webserver] ---> below line lo we mention a node server ip address
Then Run this command-->>   is <ansible -i inventory -m ping all>
#### For connect one server to another server.. there are two ways
## ONE IS PASSWORD LESS AUTHENTICATION ...... As per below points thourgh connect the one server to another server
In Ansible Master ---->>> <username@ip> : lets excute <ssh-keygen> : then Enter -->> enter ---> 
One Keypair downloaded 
Then go this path <cd /home/username/.ssh> : -->> ls ---> now one public key and private keys are came 
From above same path we excute this command: <ssh-copy-id username@nodeip> -->> thats it node ssh authentication is success
Finally cd /home/username/.ssh: ssh <username@ip> ---->>> to connect from master to node

##### SECOND ONE IS PASSWORD AUNTHENTICATION
sudo adduser <username>
sudo vi /etc/sudoers ---->>>>  in this file below ROOT Lines have to edit
<USERNAME> ALL=(ALL:ALL) NOPASSWD: ALL
then sudo vi /etc/ssh/sshd-config
In Above file Password authentication <YES>
After sudo systemctl restart sshd  --->>> to active the changes
sudo su <user name>
sai@<ip>:ll
Then install to <Ansible> by using this command sudo apt-get install ansible in Only master node
ansible --version: to check the version
Then Create a <Directory> mkdir ansible
Then we create a our own <ansible.cfg> file
In Above Ansible.cfg File ### below points must to mentioned 
vi ansible.cfg  ---> [headers] ---> host_key_check = false
After we must create a our own Inventory File <vi inventory>.
In inventory file [webserver] ---> below line lo we mention a <node server ip address> <ansible_ssh_user=username ansible_ssh_pass=userpassword>
Then Run this command-->>   is <ansible -i inventory -m ping all> 
apt-get install sshpass
ssh <username@nodeip> to connect checking all node port ---->> after installing ports each every port must to log in & logout then only connetcion are successfully added
Then Run this command-->>   is ansible -i inventory -m ping all

###### For install apache install to write a yaml
vi apache2.yaml
---
- hosts: all   ####for ip address
  become: yes   #### for root user
  tasks:
  - name: installing apache
    apt: 
      name: apache2
      state: present      ##### state is present is install start nd ## state is absent is uninstall the apache2
After write the palybook to excute the playbook command is :   ansible-playbook <yaml file name> -i inventory


#### For install apache install to write a yaml
vi nginx.yaml
---
- hosts: all   ####for ip address
  become: yes   #### for root user
  tasks:
  - name: installing nginx
    apt: 
      name: nginx
      state: present      ##### state is present is install start nd ## state is absent is uninstall the apache2
After write the palybook to excute the playbook command is :   ansible-playbook <yaml file name> -i inventory

##### For java install on workernodes from master noed
---
hosts: all
become: yes
tasks:
 -name: install java
  apt:
    name: defalut-jre
    state: present

#### for install nexus on three servers

---
- hosts: all
  become: yes
  tasks:
  - name: install nexus
    apt:
      name: wget https://download.sonatype.com/nexus/3/latest-unix.tar.gz OR #### wget https://download.sonatype.com/nexus/oss/nexus-2.14.4-03-bundle.zip
      state: present



##### For Install jenkins on servers by using playbook
---
- hosts: all
  become: yes
  tasks:
  - name: install jre
    apt:
      name: default-jre
      state: present
  - name: install jdk
    apt:
      name: default-jdk
      state: present
  - name: Add an apt signing key
    apt_key:
      url: https://pkg.jenkins.io/debian-stable/jenkins.io.key
      state: present
  - name: Add repo
    apt_repository:
      repo: deb http://pkg.jenkins.io/debian-stable binary/
      state: present
  - name: install jenkins
    apt:
      name: jenkins
      update_cache: yes
      state: present



